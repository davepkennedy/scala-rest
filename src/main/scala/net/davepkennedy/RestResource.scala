package net.davepkennedy

import javax.ws.rs.{Path, Produces, GET}
import javax.ws.rs.core._
import org.springframework.beans.factory.InitializingBean
import akka.actor._


/**
 * Created with IntelliJ IDEA.
 * User: dave
 * Date: 16/05/2012
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */

private class LocalActor (resource: RestResource, actor: ActorRef)  extends Actor with ActorLogging {
  def receive = {
    case s:Submit => println(s.toString); actor ! s
    case Respond (what) => println(what)
  }
}

@Path("/")
class RestResource (dataActor:ActorRef) extends InitializingBean{

  @GET
  @Produces(Array(MediaType.TEXT_PLAIN))
  def myMethod: Response = {
    println("requesting")
    localActor ! new Submit("spangle!!")
    Response.ok("this is a rest test!").build()
  }

  private var localActor: ActorRef = null
  def afterPropertiesSet() {
    println("afterPropertiesSet")
    val system = ActorSystem("MySystem")
    localActor = system.actorOf(Props(new LocalActor(this, dataActor)), name = "localActor")
  }
}
