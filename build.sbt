name := "Makespace"
scalaVersion in ThisBuild := "2.11.11"


lazy val shared = crossProject.crossType(CrossType.Pure).in(file("shared"))
  .jvmSettings()
  .jsSettings()

lazy val sharedJVM = shared.jvm
lazy val sharedJS = shared.js

lazy val client: Project = (project in file("client"))
  .settings()
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(sharedJS)

lazy val server = (project in file("server"))
  .settings()
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
  .dependsOn(sharedJVM)


//scalaJSUseMainModuleInitializer := true
