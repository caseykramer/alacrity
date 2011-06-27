Alacrity: An Embeddable Pub/Sub Messaging API
---------------------------------------------
---

I'm thinking this will be a reasonably small bit of kit to assist
the developer who wants to create an Event Driven application by
setting up simple message bus which utilizes Publish/Subscribe
for use within an application/library.  This is **not** a distributed
bus, and does not do cross-process communication (at least not now...
probably won't later too, why are you distracting me?).  I think it
may be nice to provide a way to declaritivly use Actors as message
handlers, but we'll see how things go.