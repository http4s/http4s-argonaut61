import scala.util.Properties.envOrNone

lazy val scalazVersion = settingKey[String]("The version of Scalaz used for building.")

organization := "org.http4s"
version := "0.15.8"
name := "http4s-argonaut61"
description := "argonaut-6.1 support for http4s"

scalaVersion := "2.10.6"
crossScalaVersions <<= scalaVersion(Seq(_, "2.11.8"))
scalazVersion := sys.env.getOrElse("SCALAZ_VERSION", "7.1.11")
version := scalazCrossBuild(version.value, scalazVersion.value)

def scalazCrossBuild(version: String, scalazVersion: String) =
  VersionNumber(scalazVersion).numbers match {
    case Seq(7, 1, _*) =>
      version
    case Seq(7, 2, _*) =>
      if (version.endsWith("-SNAPSHOT"))
        version.replaceFirst("-SNAPSHOT$", "a-SNAPSHOT")
      else
        s"${version}a"
  }

def specs2Version(scalazVersion: String) =
  VersionNumber(scalazVersion).numbers match {
    case Seq(7, 1, _*) => "3.8.6-scalaz-7.1"
    case Seq(7, 2, _*) => "3.8.8"
  }

libraryDependencies ++= Seq(
  "io.argonaut" %% "argonaut" % scalazCrossBuild("6.1", scalazVersion.value),
  "org.http4s" %% "http4s-jawn" % version.value,
  "org.specs2" %% "specs2-core" % specs2Version(scalazVersion.value)
)

pomExtra := {
  <url>https://github.com/http4s/http4s-argonaut61</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:github.com/http4s/http4s-argonaut61</connection>
    <developerConnection>scm:git:git@github.com:http4s/http4s-argonaut61</developerConnection>
    <url>https://github.com/http4s/http4s-argonaut61</url>
  </scm>
  <developers>
    <developer>
      <id>rossabaker</id>
      <name>Ross A. Baker</name>
    </developer>
  </developers>
}
