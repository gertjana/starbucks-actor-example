package net.addictivesoftware.starbucks

import akka.actor.{Props, ActorSystem}
import akka.routing.SmallestMailboxRouter
import scala.util.Random


object Starbucks {

  def main(args:Array[String]):Unit = {

    def waitSomeTime = Thread.sleep((Random.nextFloat*1000).toInt)

    implicit val system = ActorSystem.create("StarBucks")

    val customers = List(
      ("Penny", "Tall Latte Machiato"),
      ("Leonard", "Double Tall Cappuccino"),
      ("Bernadette", "Grande Spicy Pumpkin Latte"),
      ("Sheldon", "Double Espresso")
    )

    val employees = List(
      system.actorOf(Props[Employee], "Rajesh"),
      system.actorOf(Props[Employee], "Howard"),
      system.actorOf(Props[Employee], "Amy")
    )

    println("Opening Starbucks")
    val starBucks = system.actorOf(Props[Employee]
           .withRouter(SmallestMailboxRouter(routees=employees)), "StarBucks")

    for ((name, coffee) <- customers) {
      println(s"Customer ${name} orders a ${coffee}")
      starBucks ! Order(coffee, name)
      waitSomeTime
    }

    Thread.sleep(8000)
    println("Closing up shop")
    system.shutdown
  }
}


