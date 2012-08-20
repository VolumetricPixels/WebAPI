WebAPI
======

[![Volumetric Pixels][VP Logo]][VP Website]

![VP Bullet]What is WebAPI?
---------------
WebAPI enables the use of javascript socket protocol such as WebSocket and HTML requests
for any third party  javascript software. Though a netty filtered channel will create 
a local loopback between  the WebSocket channel and the server channel.

![VP Bullet]License
-------
WebAPI is licensed under [GNU Lesser General Public License Version 3][License], 
but with a provision that files are released under the MIT license 180 days after they 
are published. Please see the `LICENSE.txt` file for details.


![VP Bullet]Examples
--------

	@EventHandler
	public void onRequest(WebRequestEvent event) {
		HttpRequest request = event.getRequest();

		// The result is 202 (OK)
		event.setResponseStatus(HttpResponseStatus.OK);
		
		// Server all the request
		if (request.getUri().equals("/")) {
		    event.setContent("Hello from /");
		} else if (request.getUri().equals("/index.html")) {
			event.setContent("Hello from index.html");
		} else {
			event.setContent("What are you trying to do!");
		}
		event.setResponse(response);
	}
	
[VP Logo]: http://www.hawnutor.org/image/zV5pfpD.png
[VP Website]: http://www.volumetricpixels.com/
[VP Bullet]: http://www.hawnutor.org/image/AkwOSAn.png
