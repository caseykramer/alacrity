package alacrity

trait IBus {

  def publish[T <: Message](message:T)(implicit m:Manifest[T])

  def subscribe[T <: Message](handler:Handler[T])(implicit m:Manifest[T])

  def subscribe[T <: Message](handler:(T) => Unit)(implicit m:Manifest[T])
}