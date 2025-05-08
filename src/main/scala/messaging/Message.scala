package messaging

sealed trait OrderMessage

case class AddOrder(msg: Message) extends OrderMessage
case class CancelOrder(orderId: Long) extends OrderMessage
case class MatchOrder(stockId: Long) extends OrderMessage
case class Ack(message: String) extends OrderMessage

class Message {
  price: Long,
  amount: Long,
  stockId: Long,
  traderId: Int
  tradeId: Int,
  side: MsgSide
}
