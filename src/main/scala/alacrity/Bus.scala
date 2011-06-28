package alacrity

trait IBus {

  def publish(message:Message)

  def subscribe(handler:Handler[Message])

  def subscribe(handler:(Message) => Unit)
}