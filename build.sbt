import sbt.Project.projectToRef

name := "Makespace"
scalaVersion in ThisBuild := "2.11.11"


lazy val shared = crossProject.crossType(CrossType.Pure).in(file("shared"))
  .jvmSettings()
  .jsSettings()
  .jsConfigure(_ enablePlugins(ScalaJSWeb))

lazy val sharedJVM = shared.jvm
lazy val sharedJS = shared.js

lazy val client: Project = (project in file("client"))
  .settings(
    name := "client",
    jsDependencies += RuntimeDOM % "test",
    skip in packageJSDependencies := false,
    scalaJSUseMainModuleInitializer := true
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(sharedJS)

lazy val clients = Seq(client)

lazy val server = (project in file("server"))
  .settings(
    scalacOptions ++= Settings.scalacOptions,
    libraryDependencies ++= Settings.jvmDependencies.value,
    scalaJSProjects := clients,
    compile in Compile <<= (compile in Compile) dependsOn scalaJSPipeline,
    pipelineStages in Assets := Seq(scalaJSPipeline),
    pipelineStages := Seq(rjs, digest, gzip)
  )
  .enablePlugins(PlayScala, SbtWeb)
  .disablePlugins(PlayLayoutPlugin)
  .aggregate(clients.map(projectToRef): _*)
  .dependsOn(sharedJVM)

