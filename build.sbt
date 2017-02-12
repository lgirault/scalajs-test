name := "scalajs-tests"

version := "1.0"

scalaVersion := "2.12.1"


lazy val root = Project("scalajs-tests", file(".")).
  enablePlugins(ScalaJSPlugin).
  settings(
    libraryDependencies += "com.thoughtworks.binding" %%% "dom" % "latest.release",
    scalaJSModuleKind := ModuleKind.CommonJSModule,

    scalacOptions ++= Seq(
      "-deprecation", // Emit warning and location for usages of deprecated APIs.
      "-unchecked", //  Enable additional warnings where generated code depends on assumptions.
      "-feature", // Emit warning and location for usages of features that should be imported explicitly
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:postfixOps",
      //"-optimise",
      //"-Xfatal-warnings",
      "-Yno-adapted-args",
      //"-Yinline-warnings",
      "-Ywarn-dead-code",        // N.B. doesn't work well with the ??? hole
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Ywarn-unused-import"  // 2.11 only
    )
  )