package com.packt.masteringakka.bookstore.order

import com.packt.masteringakka.bookstore.common.Bootstrap
import akka.actor.ActorSystem
import com.typesafe.conductr.bundlelib.scala.LocationCache

class OrderBoot extends Bootstrap {

  def bootup(system:ActorSystem) = {
    import system.dispatcher
    val salesAssociate = system.actorOf(SalesAssociate.props, SalesAssociate.Name)
    val salesOrderView = system.actorOf(SalesOrderView.props, SalesOrderView.Name)    
    startSingleton(system, SalesOrderViewBuilder.props, SalesOrderViewBuilder.Name)
    startSingleton(system, OrderStatusEventListener.props(salesAssociate), OrderStatusEventListener.Name)
    List(new SalesOrderRoutes(salesAssociate, salesOrderView))
  }
}