package react.sortable.demo

import scala.scalajs.js
import scala.scalajs.js.annotation._
import js.JSConverters._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.raw.JsNumber
import org.scalajs.dom
import org.scalajs.dom.MouseEvent
import react.virtualized._
import react.draggable._
import Data.DataRow
import react.sortable._

object TableDemo {
  final case class Props(s: Size)

  val component = ScalaComponent.builder[Props]("TableDemo")
    .render_P{ p =>
      val view = MainTable.component
      val sortableList = SortableContainer.wrap(view)
      sortableList(
        SortableContainer.Props(
          // onSortEnd = p =>
          //   scope.modState(
          //     l => p.updated(l).toList
          //   ),
          // useDragHandle = true,
          // helperClass = "react-sortable-handler"
        )
      )(MainTable.Props(true, "index", p.s))
    }
    .build

  def apply(p: Props) = component(p)
}

object DefaultRow {
  final case class Props(p: react.virtualized.raw.RawRowRendererParameter)

  val component = ScalaComponent.builder[Props]("DefaultRow")
    .render_P{ p =>
      <.div(
        // ^.className := "sortable-hoc-item sortable-hoc-stylizedItem",
        // SortableView.handl
        react.virtualized.raw.defaultRowRenderer(p.p)
      )
    }
    .build

  def apply(p: Props) = component(p)
}

object MainTable {

  def datum(data: List[DataRow])(i: Int) = data(i % data.length)
  def rowheight(data: List[DataRow])(i: Int) = datum(data)(i).size

  final case class Widths(index: Double, name: Double, random: Double)
  final case class Props(useDynamicRowHeight: Boolean, sortBy: String, s: Size)
  final case class State(sortDirection: SortDirection, data: List[DataRow], widths: Widths)

  def headerRenderer(rs: (String, JsNumber) => Callback)(columnData: DataRow, dataKey: String, disableSort: Option[Boolean], label: VdomNode, sortByParam: Option[String], sortDirection: SortDirection): VdomNode =
    ReactFragment.withKey(dataKey)(
      <.div(
        ^.cls := "ReactVirtualized__Table__headerTruncatedText",
        label
      ),
      Draggable(Draggable.props(
          axis = Axis.X,
          defaultClassName = "DragHandle",
          defaultClassNameDragging = "DragHandleActive",
          onDrag = (ev: MouseEvent, d: DraggableData) => rs(dataKey, d.deltaX),
          position = ControlPosition(0)),
        <.span(^.cls := "DragHandleIcon", "⋮")
      )
    )

  def rowClassName(i: Int): String = i match {
    case x if x < 0      => "sortable-hoc-item sortable-hoc-stylizedItem headerRow"
    case x if x % 2 == 0 => "sortable-hoc-item sortable-hoc-stylizedItem evenRow"
    case _               => "sortable-hoc-item sortable-hoc-stylizedItem oddRow"
  }

  implicit class JsNumberOps(val d: JsNumber) extends AnyVal {
    def toDouble = (d: Any) match {
      case d: Double => d
    }
  }
  private implicit class ClickCallbackOps(val cb: OnRowClick) extends AnyVal {
    def toJsCallback: react.virtualized.raw.RawOnRowEvent =
      (i: react.virtualized.raw.RawIndexParameter) => cb(i.index).runNow()
  }


    def defaultRowRendererS[C <: js.Object]: RowRenderer[C] =
      (className: String,
       columns: Array[VdomNode],
       index: Int,
       isScrolling: Boolean,
       key: String,
       rowData: C,
       onRowClick: Option[OnRowClick],
       onRowDoubleClick: Option[OnRowClick],
       onRowMouseOut: Option[OnRowClick],
       onRowMouseOver: Option[OnRowClick],
       onRowRightClick: Option[OnRowClick],
       style: Style) => {
        val sortableItem = SortableElement.wrap(DefaultRow.component)
        sortableItem(
          SortableElement.Props(index = index))(DefaultRow.Props(
            react.virtualized.raw.RawRowRendererParameter(
              className,
              columns.map(_.rawNode).toJSArray,
              index,
              isScrolling,
              key,
              rowData,
              onRowClick.map(_.toJsCallback).orUndefined,
              onRowDoubleClick.map(_.toJsCallback).orUndefined,
              onRowMouseOut.map(_.toJsCallback).orUndefined,
              onRowMouseOver.map(_.toJsCallback).orUndefined,
              onRowRightClick.map(_.toJsCallback).orUndefined,
              Style.toJsObject(style)
            ))
          )

      }

  val component = ScalaComponent.builder[Props]("TableDemo")
    .initialState(State(SortDirection.ASC, Data.generateRandomList, Widths(0.1, 0.4, 0.6)))
    .renderPS{($, props, state) =>
      def resizeRow(k: String, dx: JsNumber): Callback =
        $.modState { s =>
          val percentDelta = dx.toDouble / props.s.width
          k match {
            case "index" => s.copy(widths = s.widths.copy(s.widths.index + percentDelta, s.widths.name - percentDelta, s.widths.random - percentDelta))
            case "name" => s.copy(widths = s.widths.copy(s.widths.index + percentDelta, s.widths.name + percentDelta, s.widths.random - percentDelta))
            case "randow" => s.copy(widths = s.widths.copy(s.widths.index + percentDelta, s.widths.name + percentDelta, s.widths.random + percentDelta))
          }
        }

      def sort(index: String, sortDirection: SortDirection): Callback = {
        val sorted = state.data.sortBy(_.index)
        $.setState(state.copy(data = if (sortDirection == SortDirection.ASC) sorted else sorted.reverse, sortDirection = sortDirection))
      }

      val columns = List(
        Column(Column.props((props.s.width * state.widths.index).toInt, "index", label = "Index", disableSort = false, headerRenderer = headerRenderer(resizeRow))),
        Column(Column.props((props.s.width * state.widths.name).toInt, "name", label = "Full Name", disableSort = false, headerRenderer = headerRenderer(resizeRow))),
        Column(Column.props((props.s.width * state.widths.random).toInt, "random", disableSort = true, className = "exampleColumn", label = "The description label is so long it will be truncated", flexGrow = 1, cellRenderer = (cellData: DataRow, _: js.Any, _: String, _: js.Any, _: Int) => cellData.toString, headerRenderer = headerRenderer(resizeRow)))
      )
      val t = Table(
        Table.props(
          disableHeader = false,
          noRowsRenderer = () => <.div(^.cls := "noRows", "No rows"),
          overscanRowCount = 10,
          rowClassName = rowClassName _,
          height = 270,
          rowCount = 1000,
          rowHeight = if (props.useDynamicRowHeight) rowheight(state.data) _ else 40,
          onRowClick = x => Callback.log(x),
          width = props.s.width.toInt,
          rowGetter = datum(state.data),
          headerClassName = "headerColumn",
          sort = sort _,
          sortBy = props.sortBy,
          sortDirection = state.sortDirection,
          rowRenderer = defaultRowRendererS,
          headerHeight = 30), columns: _*)
      t.mapMounted(_.raw.scrollToRow(20))
      t
    }
    .build

  def apply(p: Props) = component(p)
}

@JSExportTopLevel("Demo")
object Demo {
  @JSImport("react-virtualized/styles.css", JSImport.Default)
  @js.native
  object ReactVirtualizedStyles extends js.Object

  @JSExport
  def main(): Unit = {
    // needed to load the styles
    ReactVirtualizedStyles
    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }
    val tableF = (s: Size) =>
      TableDemo(TableDemo.Props(s)).vdomElement

    AutoSizer(AutoSizer.props(tableF, disableHeight = true)).renderIntoDOM(container)
    ()
  }
}
