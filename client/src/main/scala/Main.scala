import scala.scalajs.js
import org.scalajs.dom
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.WebpackRequire
import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Router, RouterConfigDsl, RouterCtl}

sealed trait Pages
case object Page1 extends Pages
case object Page2 extends Pages

object Main extends js.JSApp {

  val routerConfig = RouterConfigDsl[Pages].buildConfig { dsl =>
    import dsl._
    (
      staticRoute(root, Page1) ~> renderR(ctrl => Comp1.comp1(Comp1.Props("Root page", ctrl)))
    | staticRoute("/#test", Page2) ~> renderR(ctrl => Comp1.comp1(Comp1.Props("test page", ctrl)))
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
