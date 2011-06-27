package alacrity

trait Handler[TMessage <: Message] {
  def Handle(message:TMessage)
}