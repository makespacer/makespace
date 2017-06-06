package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import play.api.{Configuration, Environment}

class Application @Inject() (implicit val config: Configuration, env: Environment) extends Controller {
  def index = Action {
    val route = routes.Assets.versioned("client")
    Ok(views.html.index("Index" + route))
  }
}
