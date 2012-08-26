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
WebAPI is licensed under the [Affero General Public License Version 3][License]. Please see the `LICENSE.txt` file for details.

![VP Bullet]The Team
-------
<table>
	<tr>
		<th><img src="http://cdn.spout.org/data/avatars/l/5/5138.jpg?1345138056"/><br/>Wolftein</th>
	</tr>
</table>

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
	
![VP Bullet]Compiling
---------
WebAPI uses Maven to handle its dependencies.

* Install [Maven 2 or 3](http://maven.apache.org/download.html)  
* Checkout this repo and run: `mvn clean package install`

![VP Bullet]Coding and Pull Request Formatting
----------------------------------
* Generally follow the Oracle coding standards.
* Use tabs, no spaces.
* No trailing whitespaces.
* 200 column limit for readability.
* Pull requests must compile, work, and be formatted properly.
* Sign-off on ALL your commits - this indicates you agree to the terms of our license.
* No merges should be included in pull requests unless the pull request's purpose is a merge.
* Number of commits in a pull request should be kept to *one commit* and all additional commits must be *squashed*.
* You may have more than one commit in a pull request if the commits are separate changes, otherwise squash them.

**Please follow the above conventions if you want your pull request(s) accepted.**

[VP Logo]: http://www.hawnutor.org/image/zV5pfpD.png
[VP Website]: http://www.volumetricpixels.com/
[VP Bullet]: http://www.hawnutor.org/image/AkwOSAn.png

