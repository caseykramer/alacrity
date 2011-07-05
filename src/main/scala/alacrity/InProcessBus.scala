package alacrity

class InProcessBus extends IBus {

  var myHandler = Map[Manifest[_],List[Handler[_]]]()

  def publish[T <: Message](message:T)(implicit m:Manifest[T]) {
    myHandler.filter(k => k._1 >:> m)
             .foreach(k => k._2.foreach(h => h.asInstanceOf[Handler[T]].Handle(message)))
  }

  def subscribe[T <: Message](handler:Handler[T])(implicit m:Manifest[T]) {
    myHandler.get(m) match {
      case Some(h) => myHandler = myHandler + (m -> h.+:(handler))
      case None => myHandler = myHandler + (m -> List(handler))
    }
  }

  def subscribe[T <: Message](handler:T => Unit)(implicit m:Manifest[T]) {
    myHandler.get(m) match {
      case Some(h) =>  myHandler = myHandler + (m -> h.+:(Handler(handler)))
      case None => myHandler = myHandler + (m -> List(Handler(handler)))
    }
  }
}

object Bus {
  def apply() = { new InProcessBus() }
}