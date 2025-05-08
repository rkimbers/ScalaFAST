package books

class Node (
  var value: Long = 0L,
  var owner: OrderNode = null,
  var prev: Option[Node] = None,
  var next: Option[Node] = None
  ) {
  def init(owner: OrderNode, value: Long): Unit = {
    this.value = value
    this.owner = owner
    this.prev = None
    this.next = None
  }

  def pop(): Unit = {
    prev.foreach(_.next = this.next)
    next.foreach(_.prev = this.prev)

    this.prev = None
    this.next = None
  }

  def insertAfter(afterNode: Node): Unit = {
    this.prev = Some(afterNode)
    this.next = afterNode.next

    afterNode.next.foreach(_.prev = Some(this))
    afterNode.next = Some(this)
  }

  override def toString: String = {
    s"Node(value=$value, owner=${if (owner != null) owner.toString else "null"}"
  }


}
