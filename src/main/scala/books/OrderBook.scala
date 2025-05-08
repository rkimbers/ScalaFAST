package books

scala.collection.mutable

class OrderBook(isBuySide: Boolean) {

  private val ordering =
    if (isBuySide) Ordering[Long].reverse
    else Ordering[Long]

  private val priceLevels = mutable.TreeMap[Long, mutable.Queue[OrderNode]]()(ordering)
  private val orderMap = mutable.HashMap[Long, OrderNode]()
  private var bestPrice: Option[Long] = None

  def addOrder(order: OrderNode): Unit = {
    val price = order.price
    val queue = priceLevels.getOrElseUpdate(price, mutable.Queue[OrderNode]())
    queue.enqueue(order)
    orderMap(order.guid) = order

    bestPrice = Some(priceLevels.head._1)
  }

  def cancelOrder(orderId: Long): Boolean = {
    orderMap.get(orderId) match {
      case Some(order) =>
        val price = order.price
        val queue = priceLevels(price)

        queue.dequeueFirst(_.guid == orderId)

        if (queue.isEmpty) {
          priceLevels -= price
        }

        orderMap -= orderId

        bestPrice = priceLevels.headOption.map(_._1)

        true

      case None =>
        false
    }
  }

  def getBestPrice: Option[Long] = bestPrice

  def peekBestOrder: Option[OrderNode] = {
    priceLevels.headOption.flatMap(_._2.headOption)
  }
}
