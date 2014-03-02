name := "aidreg-web"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache
)

libraryDependencies ++= Seq(
  "redis.clients" % "jedis" % "2.1.0"
)

play.Project.playJavaSettings