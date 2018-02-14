package react.semanticui.elements.icon

import utest._
import japgolly.scalajs.react.test._
import react.semanticui.sizes._

object IconTests extends TestSuite {
  val tests = Tests {
    'render - {
      val icon = Icon(Icon.props(name = "edit"))
      ReactTestUtils.withRenderedIntoDocument(icon) { m =>
        assert(m.outerHtmlScrubbed() == """<i aria-hidden="true" class="edit icon"></i>""")
      }
    }
    'renderFitted - {
      val icon = Icon(Icon.props(fitted = true, name = "edit"))
      ReactTestUtils.withRenderedIntoDocument(icon) { m =>
        assert(m.outerHtmlScrubbed() == """<i aria-hidden="true" class="edit fitted icon"></i>""")
      }
    }
    'renderClassName - {
      val icon = Icon(Icon.props(className = "another", name = "edit"))
      ReactTestUtils.withRenderedIntoDocument(icon) { m =>
        assert(m.outerHtmlScrubbed() == """<i aria-hidden="true" class="edit icon another"></i>""")
      }
    }
    'size - {
      val icon = Icon(Icon.props(size = Big, name = "edit"))
      ReactTestUtils.withRenderedIntoDocument(icon) { m =>
        println(m.outerHtmlScrubbed)
        assert(m.outerHtmlScrubbed() == """<i aria-hidden="true" class="edit big icon"></i>""")
      }
    }
  }
}
