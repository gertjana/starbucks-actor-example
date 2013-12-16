package net.addictivesoftware.starbucks

import akka.actor.{Props, ActorSystem}
import akka.routing.{RoundRobinRouter, SmallestMailboxRouter}
import scala.util.Random


object Starbucks extends App {
  def waitSomeTime = Thread.sleep((Random.nextFloat*1000).toInt)

  implicit val system = ActorSystem.create("StarBucks")

  if (args.size > 0 && (args(0) equals "remote"))  {
    println("only starting remote system")
  } else {

    val customers = List(
      ("Penny", "Tall Latte Machiato"),
      ("Leonard", "Double Tall Cappuccino"),
      ("Bernadette", "Grande Spicy Pumpkin Latte"),
      ("Sheldon", "Double Espresso")
    )

    val employees = List(
      system.actorOf(Props[Employee], "Raj"),
      system.actorOf(Props[Employee], "Howard"),
      system.actorOf(Props[Employee], "Amy"),
      system.actorOf(Props[Employee], "Leslie")
    )

    println("Opening Starbucks")
    val starBucks = system.actorOf(Props[Employee]
      .withRouter(RoundRobinRouter(routees=employees)),"StarBucks")

    customers foreach { request =>
      println(s"Customer ${request._1} orders a ${request._2}")
      starBucks ! Order(request._2, request._1)
      waitSomeTime
    }

    Thread.sleep(8000)
    println("Closing up shop")
    system.shutdown
  }

}


