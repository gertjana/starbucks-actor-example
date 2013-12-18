package net.addictivesoftware.starbucks

import akka.actor.{Props, ActorSystem}
import akka.routing.SmallestMailboxRouter
import scala.util.Random


object Starbucks {

  def main(args:Array[String]):Unit = {

    implicit val system = ActorSystem.create("StarBucks")

  }
}


