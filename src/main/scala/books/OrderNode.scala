package books

case class OrderNode(
    priceNode: Node,
    guidNode: Node,
    var amount: Long,
    stockId: Long,
    kind: MsgKind,
    var nextFree: Option[OrderNode] = None
) {

    def copyFrom(from: Message): Unit = {
        amount = from.amount
        stockId = from.stockId
        kind = from.kind
        setup(from.price, combineInt32(from.traderId.toInt, from.tradeId.toInt))
    }

    def copyTo(from: Message): Unit = {
        to.kind = kind
        to.price = price
        to.amount = amount
        to.traderId = traderId
        to.tradeId = tradeId
        to.stockId = stockId
    }

    def setup(price: Long, guid: Long): Unit = {
        priceNode.init(this, price)
        guidNode.init(this, guid)
    }
    
    def price: Long = priceNode.value

    def guid: Long = guidNode.value

    def traderId: Int = highInt32(guid)

    def tradeId: Int = lowInt32(guid)
    
    def amount: Long = amount

    def reduceAmount(s: Long): Unit = {
        amount -= s
    }

    def stockId: Long = stockId

    def kind: MsgKind = kind

    def remove(): Unit = {
    priceNode.pop()
    guidNode.pop()
    }

    override def toString: String = {
    if (this == null) "<nil>"
    else {
      val priceStr = formatWithComma(price)
      val amountStr = formatWithComma(amount)
      val traderIdStr = formatWithDash(traderId)
      val tradeIdStr = formatWithDash(tradeId)
      val stockIdStr = formatWithDash(stockId)
      s"$kind, price $priceStr, amount $amountStr, trader $traderIdStr, trade $tradeIdStr, stock $stockIdStr"
        }
    }

    private def highInt32(value: Long): Int = (value >> 32).toInt
    private def lowInt32(value: Long): Int = value.toInt
    private def combineInt32(high: Int, low: Int): Long = ((high.toLong << 32) | (low & 0xFFFFFFFFL))

    private def formatWithComma(value: Long): String = f"$value%,d"
    private def formatWithDash(value: Int): String = f"$value%-d"

}