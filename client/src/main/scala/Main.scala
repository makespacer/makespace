import diode._
import diode.react.{ModelProxy, ReactConnector}

import scala.scalajs.js
import org.scalajs.dom
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.WebpackRequire
import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Router, RouterConfigDsl, RouterCtl}

sealed trait Pages
case object Page1 extends Pages
case object Page2 extends Pages

case class RootModel(data1: String, data2: Int)

case class ChangeData1(newVal: String) extends Action

object AppCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {

  override def initialModel: RootModel = RootModel("testData1", 123)

  val data1Reader = AppCircuit.zoom(_.data1)

  val firstHandler = new ActionHandler(AppCircuit.zoomTo(_.data1)) {
    override def handle = {
      case ChangeData1(newVal) => updated(newVal)
    }
  }

  override val actionHandler = composeHandlers(firstHandler)

}

object Main extends js.JSApp {

  val data1Connection = AppCircuit.connect(_.data1)

  val routerConfig = RouterConfigDsl[Pages].buildConfig { dsl =>
    import dsl._
    (
      staticRoute(root, Page1) ~> renderR {ctrl =>
        Comp1.comp1(Comp1.Props("Root page", ctrl))
      }
    | staticRoute("/#test", Page2) ~> renderR { ctrl =>
        data1Connection(proxy => Comp2.comp2(Comp2.Props(proxy)))
      }
      ).notFound(redirectToPage(Page1)(Redirect.Replace))
  }

  def require(): Unit = {
    WebpackRequire.React
    WebpackRequire.ReactDOM
    ()
  }


  override def main(): Unit = {
    println("Hello world..")
    require()
    val domTarget = dom.document.getElementById("root")
    val router = Router(BaseUrl.fromWindowOrigin, routerConfig)
    router().renderIntoDOM(domTarget)
  }

}

object Comp1 {
  case class Props(theProp: String, routerCtl: RouterCtl[Pages])

  val comp1 = ScalaComponent.builder[Props]("MyComp")
    .render_P(p => <.div(
      "hello2 " + p.theProp,
      ^.onClick --> p.routerCtl.set(Page2)))
    .build
}

object Comp2 {
  case class Props(proxy: ModelProxy[String])

  val comp2 = ScalaComponent.builder[Props]("MyComp")
    .render_P {p =>
      <.div(
      "hello2 " + p.proxy.value,
      ^.onClick --> p.proxy.dispatchCB(ChangeData1("New value"))
      )
    }
    .build
}
