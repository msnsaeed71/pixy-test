# pixy-test
test pixy

Protocol specification

Guacamole is designed to be extensible in a way that any future protocols can be added to it with minimum effort.
Guacamole achieves this by abstracting and translating all of the supported protocols to an 
intermediate custom protocol (unsurprisingly, called Guacamole protocol).
This intermediate text-based protocol is based on drawing instructions inspired by Porter-Duff.
Regardless of the underlying protocol, it always gets translated into the guacamole protocol.
You can see the specification of the protocol in the corresponding section of the official documentation.

Adoption of the protocol in our stack

The process of recording and capturing the session is achieved by simply dumping the wire protocol
into flat file system files. We do the payback by simply reading from those files and sending them to the client.
Files are separated into 2 different streams, client stream and server stream.
All the data that is sent from the browser to the server is considered client stream,
and all the data that is sent back from the server is called server stream. 
They get stored in different files, one for client and one for the server.
