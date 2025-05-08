package books

scala.collection.mutable

class OrderBook {
  private val priceLevels = mutable.TreeMap[Long, mutable.Queue[OrderNode]]()(Ordering[Long].reverse)  //buy side
  private val orderMap = mutable.HashMap[Long, OrderNode]()
  private var bestPrice: Option[Long] = None

  def addOrder(order: OrderNode): Unit = {
    val price = order.price

    val queue = priceLevels.getOrElseUpdate(price, mutable.Queue[OrderNode]())

    orderMap(order.guid) = order

    if(bestPrice.isEmpty() || price > bestPrice.get){
      bestPrice = Some(price)
    }
  }

  def cancelOrder(orderId: Long): Boolean = {
    orderMap.get(orderId) match {
      case Some(order) =>
        val price = order.price
        val queue = priceLevels(price)package books

    scala.collection.mutable

    class OrderBook {
    private val priceLevels = mutable.TreeMap[Long, mutable.Queue[OrderNode]]()(Ordering[Long].reverse)  //buy side
    private val orderMap = mutable.HashMap[Long, OrderNode]()
    private var bestPrice: Option[Long] = None

    def addOrder(order: OrderNode): Unit = {
    val price = order.price

    val queue = priceLevels.getOrElseUpdate(price, mutable.Queue[OrderNode]())

    orderMap(order.guid) = order

    if(bestPrice.isEmpty() || price > bestPrice.get){
    bestPrice = Some(price)
    }
    }

    def cancelOrder(orderId: Long): Boolean = {
    orderMap.get(orderId) match {
    case Some(order) =>
    val price = order.price
    val queue = priceLevels(price)

    queue.dequeueFirst(_.guid == orderId)

    if(queue.isEmpty){
    priceLevels -= price
    }

    orderMap -= orderId

    if(bestPrice.contains(price) && queue.isEmpty){
    bestPrice = priceLevels.headOption.map(_._1)
    }

    true

    case Node =>
    false
    }
    }

    def getBestPrice: Option[Long] = bestPrice
    def getBestPrice: Option[Long] = priceLevels.headOption.map(_._1)
    }


    queue.dequeueFirst(_.guid == orderId)

        if(queue.isEmpty){
          priceLevels -= price
        }

        orderMap -= orderId

        if(bestPrice.contains(price) && queue.isEmpty){
          bestPrice = priceLevels.headOption.map(_._1)
        }

        true

      case Node =>
        false
    }
  }

  def getBestPrice: Option[Long] = bestPrice
  def getBestPrice: Option[Long] = priceLevels.headOption.map(_._1)
}
