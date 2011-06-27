package alacrity

object Bus {
  def publish[TMessage <: Message](message:TMessage) {
  }

  def subscribe[TMessage <: Message](handler:Handler[TMessage]) {
  }

  def subscribe[TMessage <: Message](handler:(TMessage) => Unit) {
  }
}