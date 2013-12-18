package net.addictivesoftware.starbucks

import akka.actor.{Props, ActorSystem}
import akka.routing.SmallestMailboxRouter
import scala.util.Random


object Starbucks {

  def main(args:Array[String]):Unit = {

    println("Starting Actor system StarBucks only")

    val system = ActorSystem.create("StarBucks")

    io.Source.stdin.getLines().toStream  //block until return key is pressed

    system.shutdown

  }
}


