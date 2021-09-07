import Settings.LibraryVersions

ThisBuild / parallelExecution := false
Test / parallelExecution := false

Global / cancelable := true

addCommandAlias("restartWDS", "; demo/fastOptJS::startWebpackDevServer; ~demo/fastOptJS")

Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(
  List(
    homepage := Some(url("https://github.com/cquiroz/scalajs-react-sortable-hoc")),
    licenses := Seq("BSD 3-Clause License" -> url("https://opensource.org/licenses/BSD-3-Clause")),
    developers := List(
      Developer("cquiroz",
                "Carlos Quiroz",
                "carlos.m.quiroz@gmail.com",
                url("https://github.com/cquiroz")
      )
    ),
    scmInfo := Some(
      ScmInfo(url("https://github.com/cquiroz/scalajs-react-sortable-hoc"),
              "scm:git:git@github.com:cquiroz/scalajs-react-sortable-hoc.git"
      )
    )
  )
)

val root =
  project
    .in(file("."))
    .settings(commonSettings: _*)
    .aggregate(facade, demo)
    .settings(
      name := "root",
      // No, SBT, we don't want any artifacts for root.
      // No, not even an empty jar.
      publish := {},
      publishLocal := {},
      publishArtifact := false,
      Keys.`package` := file("")
    )

lazy val demo =
  project
    .in(file("demo"))
    .enablePlugins(ScalaJSBundlerPlugin)
    .settings(commonSettings: _*)
    .settings(
      webpack / version := "4.30.0",
      startWebpackDevServer / version := "3.3.1",
      fastOptJS / webpackConfigFile := Some(
        baseDirectory.value / "src" / "webpack" / "webpack-dev.config.js"
      ),
      fullOptJS / webpackConfigFile := Some(
        baseDirectory.value / "src" / "webpack" / "webpack-prod.config.js"
      ),
      webpackMonitoredDirectories += (Compile / resourceDirectory).value,
      webpackResources := (baseDirectory.value / "src" / "webpack") * "*.js",
      webpackMonitoredFiles / includeFilter := "*",
      useYarn := true,
      fastOptJS / webpackBundlingMode := BundlingMode.LibraryOnly(),
      fullOptJS / webpackBundlingMode := BundlingMode.Application,
      test := {},
      webpackDevServerPort := 6060,
      Compile / npmDevDependencies ++= Seq(
        "css-loader"                         -> "1.0.0",
        "less"                               -> "3.8.1",
        "less-loader"                        -> "4.1.0",
        "mini-css-extract-plugin"            -> "0.4.3",
        "html-webpack-plugin"                -> "3.2.0",
        "url-loader"                         -> "1.1.1",
        "style-loader"                       -> "0.23.0",
        "postcss-loader"                     -> "3.0.0",
        "cssnano"                            -> "4.1.0",
        "optimize-css-assets-webpack-plugin" -> "5.0.1",
        "webpack-merge"                      -> "4.1.4",
        "webpack-dev-server-status-bar"      -> "1.1.0",
        "autoprefixer"                       -> "9.1.5"
      ),
      Compile / npmDependencies ++= Seq(
        "react"              -> LibraryVersions.reactJS,
        "react-dom"          -> LibraryVersions.reactJS,
        "react-sortable-hoc" -> LibraryVersions.reactSortableHOC
      ),
      libraryDependencies ++= Seq(
        "io.github.cquiroz.react" %%% "react-virtualized" % LibraryVersions.reactVirtualized,
        "io.github.cquiroz.react" %%% "react-draggable"   % LibraryVersions.reactDraggable
      ),
      // don't publish the demo
      publish := {},
      publishLocal := {},
      publishArtifact := false,
      Keys.`package` := file("")
    )
    .dependsOn(facade)

lazy val facade =
  project
    .in(file("facade"))
    .enablePlugins(ScalaJSBundlerPlugin)
    .settings(commonSettings: _*)
    .settings(
      name := "react-sortable-hoc",
      webpack / version := "4.30.0",
      startWebpackDevServer / version := "3.3.1",
      // Requires the DOM for tests
      Test / requireJsDomEnv := true,
      // installJsdom / version := "12.0.0",
      // Compile tests to JS using fast-optimisation
      // Test / scalaJSStage            := FastOptStage,
      Compile / npmDependencies ++= Seq(
        "react"              -> LibraryVersions.reactJS,
        "react-dom"          -> LibraryVersions.reactJS,
        "react-sortable-hoc" -> LibraryVersions.reactSortableHOC
      ),
      libraryDependencies ++= Seq(
        "com.github.japgolly.scalajs-react" %%% "core"        % LibraryVersions.scalaJsReact,
        "com.github.japgolly.scalajs-react" %%% "extra"       % LibraryVersions.scalaJsReact,
        "org.scala-js"                      %%% "scalajs-dom" % LibraryVersions.scalaJSDom,
        "com.github.japgolly.scalajs-react" %%% "test"        % LibraryVersions.scalaJsReact % Test,
        "com.lihaoyi"                       %%% "utest"       % LibraryVersions.utest % Test,
        "org.typelevel"                     %%% "cats-core"   % LibraryVersions.cats % Test
      ),
      Test / webpackConfigFile := Some(
        baseDirectory.value / "src" / "webpack" / "test.webpack.config.js"
      ),
      testFrameworks += new TestFramework("utest.runner.Framework")
    )

lazy val commonSettings = Seq(
  scalaVersion := "2.13.4",
  organization := "io.github.cquiroz.react",
  sonatypeProfileName := "io.github.cquiroz",
  description := "scala.js facade for react-sortable-hoc ",
  Test / publishArtifact := false,
  scalacOptions ~= (_.filterNot(
    Set(
      // By necessity facades will have unused params
      "-Wdead-code",
      "-Wunused:params",
      "-Wunused:explicits",
      "-Ywarn-dead-code",
      "-Ywarn-unused:params"
    )
  ))
)
