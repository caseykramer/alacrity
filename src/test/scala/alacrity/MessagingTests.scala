package alacrity

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class MessagingTests extends FlatSpec with ShouldMatchers {

  "The Bus" should "allow me to register a function as a subscriber" in {
      val bus = Bus()
      var gotMessage = false;
      bus.subscribe((m:Message) => gotMessage = true)

      bus.publish(new Message {})

      gotMessage should be(true)
    }

    it should "allow me to register an instance of Handler as a subscriber" in {
      val bus = Bus()

      val handler = new Handler[Message] {
        var gotMessage = false

        def Handle(message:Message) {
          gotMessage = true
        }
      }

      bus.subscribe(handler)
      bus.publish(new Message {})

      handler.gotMessage should be(true)

    }

    it should "allow me to have multiple handlers" in {
      val bus = Bus()

      val handler1 = new Handler[Message] {
        var gotMessage = false

        def Handle(message:Message) {
          gotMessage = true
        }
      }

      val handler2 = new Handler[Message] {
        var gotMessage = false

        def Handle(message:Message) {
          gotMessage = true
        }
      }

      bus.subscribe[Message](handler1)
      bus.subscribe[Message](handler2)

      bus.publish[Message](new Message {})

      handler1.gotMessage should be(true)
      handler2.gotMessage should be(true)
    }

    it should "execute handlers based on message type" in {
      trait NewMessage extends Message {}

      val message = new NewMessage {}

      val bus = Bus()

      val handler1 = new Handler[Message] {
        var gotMessage = false

        def Handle(message:Message) {
          gotMessage = true
        }
      }

      val handler2 = new Handler[NewMessage] {
        var gotMessage = false

        def Handle(message:NewMessage) {
          gotMessage = true
        }
      }

      bus.subscribe[Message](handler1)
      bus.subscribe[NewMessage](handler2)

      bus.publish[Message](message)

      handler1.gotMessage should be(true)
      handler2.gotMessage should be(false)

    }

}