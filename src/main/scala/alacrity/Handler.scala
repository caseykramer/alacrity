package alacrity

trait Handler[-TMessage <: Message] {
  def Handle(message:TMessage)
}

object Handler {
  def apply[T <: Message](fun:T => Unit) =
    new Handler[T] {
      def Handle(message:T) { fun(message)}
    }
}