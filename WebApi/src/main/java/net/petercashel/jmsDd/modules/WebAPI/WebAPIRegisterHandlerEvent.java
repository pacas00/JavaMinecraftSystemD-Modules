package net.petercashel.jmsDd.modules.WebAPI;

import com.sun.net.httpserver.HttpServer;

public class WebAPIRegisterHandlerEvent {
	public HttpServer serverInstance;

	public WebAPIRegisterHandlerEvent(HttpServer server) {
		serverInstance = server;
	}

}
