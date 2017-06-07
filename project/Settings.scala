import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Settings {

  val scalacOptions = Seq(
    "-Xlint",
    "-unchecked",
    "-deprecation",
    "-feature"
  )

  object versions {
    val scalajsScripts = "1.0.0"
  }

  val jvmDependencies = Def.setting(Seq(
    "com.vmunier" %% "scalajs-scripts" % versions.scalajsScripts
  ))

  val scalajsDependencies = Def.setting(Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % "1.0.0",
    "com.github.japgolly.scalajs-react" %%% "extra" % "1.0.0"
  ))

}