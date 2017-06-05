name := "Makespace"
scalaVersion in ThisBuild := "2.11.8"

lazy val root = project.in(file(".")).
aggregate(buildJS, buildJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val buildCross = crossProject.in(file(".")).
  jvmSettings().
  jsSettings()

//scalaJSUseMainModuleInitializer := true

lazy val buildJVM = buildCross.jvm
lazy val buildJS = buildCross.js
