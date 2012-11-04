import sbt._

import Keys._
import AndroidKeys._

object General {

  val libRoboSpecs = "com.github.jbrechtel" %% "robospecs" % "0.2" % "test"
  val libMockito = "org.mockito" % "mockito-all" % "1.9.5" % "test"
  val libSpecs2 = "org.specs2" %% "specs2" % "1.12.2" % "test"
  val libRobolectric = "com.pivotallabs" % "robolectric" % "1.1-jar-with-dependencies" % "test"
  val scalaTest = "org.scalatest" %% "scalatest" % "1.8" % "test"

  val settings = Defaults.defaultSettings ++ Seq (
    name := "hailscala",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.2",
    platformName in Android := "android-10"
  )

  val proguardSettings = Seq (
    useProguard in Android := false
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",
      libraryDependencies ++= Seq(libRoboSpecs, libMockito, libSpecs2, libRobolectric, scalaTest)
    )
}

object AndroidBuild extends Build {

  lazy val main = Project (
    "hailscala",
    file("."),
    settings = General.fullAndroidSettings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "hailscalaTests"
    )
  ) dependsOn main
}
