import scala.scalajs.js
import org.scalajs.dom
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.WebpackRequire

object Main extends js.JSApp {

  def require(): Unit = {
    WebpackRequire.React
    WebpackRequire.ReactDOM
    ()
  }

  override def main(): Unit = {
    println("Hello world..")
    require()
    val domTarget = dom.document.body
    Comp1.comp1(Comp1.Props()).renderIntoDOM(domTarget)
  }

}

object Comp1 {
  case class Props()

  val comp1 = ScalaComponent.builder[Props]("MyComp")
    .renderStatic(<.div("hello!"))
    .build
}
