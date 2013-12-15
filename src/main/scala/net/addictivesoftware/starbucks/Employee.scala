package net.addictivesoftware.starbucks

import akka.actor.Actor
import scala.util.Random

sealed trait messages
case class Order(coffee:String, name:String) extends messages
case class MakeCoffee(coffee:String, name:String) extends messages
case class CoffeeReady(coffee:String, name:String) extends messages

class Employee extends Actor {
  def waitSomeTime = Thread.sleep((Random.nextFloat*2000).toInt)

  def receive = {
    case Order(coffee, name) => {
      println(s"Employee ${self.path.name} writes '${coffee}' and '${name}' on a cup")
      waitSomeTime
      context.actorSelection("/user/StarBucks") ! MakeCoffee(coffee, name)
    }
    case MakeCoffee(coffee, name) => {
      println(s"Employee ${self.path.name} makes a ${coffee} for ${name} ")
      waitSomeTime
      context.actorSelection("/user/StarBucks") ! CoffeeReady(coffee, name)
    }
    case CoffeeReady(coffee, name) => {
      println(s"Employee ${self.path.name} shouts: ${coffee} for ${name} is ready!")
    }
  }
}
