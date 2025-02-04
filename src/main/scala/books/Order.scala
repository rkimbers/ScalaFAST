case class OrderNode(
    priceNode: Node,
    guidNode: Node,
    var amount: Long,
    stockId: Long,
    kind: MsgKind,
    var nextFree: Option[OderNode] = None
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
        initNode(this, price, priceNode, guidNode)
        initNode(this, guid, guidNode, priceNode)
    }
    
    def price: Long = priceNode.value

    def guid: Long = guidNode.value

    def traderId: Int = highInt32(guid)

    def tradeId: Int lowInt32(guid)
    
    def amount(): Long = amount

    def reduceAmount(s: Long): Unit = {
        amount -= s
    }

    def stockId(): Long = stockId

    def kind(): MsgKind = kind()

}