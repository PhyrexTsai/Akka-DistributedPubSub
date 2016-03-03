import akka.actor.{Props, ActorSystem}

/**
  * Created by Phyrex on 2016/3/4.
  */
object Main {
  def main(args : Array[String]) : Unit = {
    val system = ActorSystem("system")
    val topic = "chat"
    val c1 = system.actorOf(Props(classOf[PubSub], topic), "c1")
    Thread.sleep(100)
    val c2 = system.actorOf(Props(classOf[PubSub], topic), "c2")
    Thread.sleep(100)
    val c3 = system.actorOf(Props(classOf[PubSub], topic), "c3")
    Thread.sleep(100)
    c1 ! Event.Send(c1.path.name, "hi")
    Thread.sleep(100)
    c2 ! Event.Leave(c2.path.name)
    Thread.sleep(100)
    system.shutdown()
  }
}
