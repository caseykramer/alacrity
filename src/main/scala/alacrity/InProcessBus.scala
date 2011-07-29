package alacrity

import scala.ref.WeakReference

class InProcessBus extends IBus {

  var myHandler = Map[Manifest[_],List[WeakReference[Handler[_]]]]()

  def publish[T <: Message](message:T)(implicit m:Manifest[T]) {
    myHandler.filter(k => k._1 >:> m)
             .foreach(k => k._2.filter(r => r.get.isDefined).foreach(h => h.get.get.asInstanceOf[Handler[T]].Handle(message)))
  }

  def subscribe[T <: Message](handler:Handler[T])(implicit m:Manifest[T]) {
    val handlerRef = new WeakReference(handler).asInstanceOf[WeakReference[Handler[_]]]
    myHandler.get(m) match {
      case Some(h) => myHandler = myHandler + (m -> h.filter(r => r.get.isDefined).+:(handlerRef))
      case None => myHandler = myHandler + (m -> List(handlerRef))
    }
  }

  def subscribe[T <: Message](handler:T => Unit)(implicit m:Manifest[T]) {
    val handlerRef = new WeakReference(Handler(handler)).asInstanceOf[WeakReference[Handler[_]]]
    myHandler.get(m) match {
      case Some(h) =>  myHandler = myHandler + (m -> h.filter(r => r.get.isDefined).+:(handlerRef))
      case None => myHandler = myHandler + (m -> List(handlerRef))
    }
  }
}

object Bus {
  def apply() = { new InProcessBus() }
}