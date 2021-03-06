package org.multibot

object Multibottest {
  def main(args: Array[String]): Unit = {
    val cache = InterpretersCache(List("#scala", "#scalaz", "#dev-ua/scala"))
    val PRODUCTION = Option(System getenv "multibot.production") exists (_.toBoolean)
    val gitterPass = Option(System getenv "multibot.gitter.pass").getOrElse("709182327498f5ee393dbb0bc6e440975fa316e5")
    Multibot(cache, if (PRODUCTION) "multibot_" else "multibot__",
      if (PRODUCTION)
        List("#clojure.pl", "#scala.pl", "#scala", "#scalaz", "#scala-fr", "#lift", "#playframework",
          "#bostonpython", "#fp-in-scala", "#CourseraProgfun", "#shapeless", "#akka", "#sbt", "#scala-monocle")
      else
        List("#multibottest", "#multibottest2")
    ).start()
    Multibot(cache = cache,
      botname = if (PRODUCTION) "multibot1" else "multibot2",
      channels = if (PRODUCTION) List("#dev-ua/scala") else List("#OlegYch/multibot"),
      settings = _.setServerHostname("irc.gitter.im").setServerPassword(gitterPass).
        setSocketFactory(javax.net.ssl.SSLSocketFactory.getDefault)
    ).start()
    while (scala.io.StdIn.readLine() != "exit") Thread.sleep(1000)
    sys.exit()
  }
}
