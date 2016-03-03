import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{SubscribeAck, Unsubscribe, Publish, Subscribe}

/**
  * Created by Phyrex on 2016/3/4.
  */
object Event {
  sealed class Message
  case class Receive(sender : String, message : String) extends Message
  case class Send(sender : String, message : String) extends Message
  case class Join(sender : String) extends Message
  case class Leave(sender : String) extends Message
}

class PubSub(topic : String) extends Actor {
  val mediator = DistributedPubSub(context.system).mediator;
  mediator ! Subscribe(topic, self)

  override def preStart = {
    self ! Event.Join(self.path.name)
  }

  def receive = {
    case Event.Join(sender) => {
      mediator ! Publish(topic, Event.Receive(sender, s"$sender joined."))
    }
    case Event.Receive(sender, message) => {
      println(s"${self.path.name} <= $message")
    }
    case Event.Send(sender, message) => {
      println(s"$sender => $message")
      mediator ! Publish(topic, Event.Receive(sender, message))
    }
    case Event.Leave(sender) => {
      mediator ! Unsubscribe(topic, self)
      mediator ! Publish(topic, Event.Receive(sender, s"$sender leaved."))
    }
  }
}
