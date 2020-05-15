package react

import japgolly.scalajs.react.CallbackTo
import org.scalajs.dom.MouseEvent
import org.scalajs.dom.Element

package object sortable {
  type ShouldCancelStart = MouseEvent => CallbackTo[Boolean]

  def defaultShouldCancelStart(e: MouseEvent): CallbackTo[Boolean] =
    e.target match {
      case e: Element =>
        // Cancel sorting if the event target is an `input`, `textarea`, `select` or `option`
        val disabledElements = List("input", "textarea", "select", "option", "button")

        CallbackTo(disabledElements.contains(e.tagName.toLowerCase()))
      case _          => CallbackTo(false)
    }

  object raw {
    type ShouldCancelStartRaw = MouseEvent => Boolean
  }
}

package sortable {

  /**
    * A change in index of an item generated by dragging
    *
    * @param oldIndex The item's old index
    * @param newIndex The item's new index
    */
  case class IndexChange(oldIndex: Int, newIndex: Int)
}
