package react.sortable.demo

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import react.sortable._

object SortableContainerDemo {

  // Equivalent of ({value}) => <li>{value}</li> in original demo
  val itemView = ScalaComponent
    .builder[String]("liView")
    .render { d =>
      <.div(
        ^.className := "react-sortable-item",
        SortableView.handle,
        <.span(s"${d.props}")
      )
    }
    .build

  // As in original demo
  val sortableItem = SortableElement.wrap(itemView)

  // Equivalent of the `({items}) =>` lambda passed to SortableContainer in original demo
  val listView = ScalaComponent
    .builder[List[String]]("listView")
    .render { d =>
      <.div(
        ^.className := "react-sortable-list",
        d.props.zipWithIndex.toTagMod { case (value, index) =>
          sortableItem(SortableElement.Props(index = index))(value)
        }
      )
    }
    .build

  // As in original demo
  val sortableList = SortableContainer.wrap(listView)

  // As in original SortableComponent
  class Backend(scope: BackendScope[Unit, List[String]]) {
    def render(props: Unit, items: List[String]) =
      sortableList(
        SortableContainer.Props(
          useDragHandle = true,
          helperClass = "react-sortable-handler"
        )
      )(items)
  }

  val defaultItems = Range(0, 10).map("Item " + _).toList

  val c = ScalaComponent
    .builder[Unit]("SortableContainerDemo")
    .initialState(defaultItems)
    .backend(new Backend(_))
    .render(s => s.backend.render(s.props, s.state))
    .build

}
