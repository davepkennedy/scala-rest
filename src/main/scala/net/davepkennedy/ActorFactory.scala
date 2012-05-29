package net.davepkennedy

import akka.actor._


/**
 * Created with IntelliJ IDEA.
 * User: dave
 * Date: 16/05/2012
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */

object ActorFactory {
  val system = ActorSystem("MySystem")

  class DataActor extends Actor with ActorLogging {
    protected def receive = {
      case Submit(what) => sender ! Respond(what.reverse)
    }
  }

  def dataActor: ActorRef = {
    system.actorOf(Props[DataActor], name = "dataActor")
  }
}
