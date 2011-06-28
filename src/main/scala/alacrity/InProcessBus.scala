package alacrity

class InProcessBus extends IBus {

  var myHandler:Handler[Message] = null

  def publish(message:Message) {
    myHandler.Handle(message)
  }

  def subscribe(handler:Handler[Message]) {
    myHandler = handler
  }

  def subscribe(handler:Message => Unit) {
    myHandler = Handler(handler)
  }
}

object Bus {
  def apply() = { new InProcessBus() }
}