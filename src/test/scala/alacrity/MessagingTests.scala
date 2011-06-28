package alacrity

import alacrity._
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import tools.nsc.doc.model.Object

class MessagingTests extends Spec with ShouldMatchers {

  describe("The Bus") {
    it("should allow me to register a function as a subscriber") {
      val bus = Bus()
      var gotMessage = false;
      bus.subscribe((m:Message) => gotMessage = true)

      bus.publish(new Message {})

      gotMessage should be(true)
    }

    it("should allow me to register an instance of Handler as a subscriber") {
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
  }

}