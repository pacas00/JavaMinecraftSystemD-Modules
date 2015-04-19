package net.petercashel.jmsDd.modules.WebAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class DefaultHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exc) throws IOException {
		String response = "";
		String urlRoot = "/";
		String urlPath = exc.getRequestURI().getRawPath();
		String requestedFile = urlPath.substring(urlRoot.length());
		if (!(requestedFile == null)) {
			if (requestedFile.isEmpty()) {
				requestedFile = "index.htm";
			}
		} else {
			requestedFile = "index.htm";
		}

		File file = new File("./doc/" + requestedFile);
		byte[] encoded;
		if (file.exists()) {
			encoded = Files.readAllBytes(Paths.get("./doc/" + requestedFile));
			response = new String(encoded, Charset.defaultCharset());
			exc.sendResponseHeaders(200, response.length());
		} else {
			response = "<html><title> 404 </title> <body> 404 - Page not found </body> </html>";
			exc.sendResponseHeaders(404, response.length());
		}
		OutputStream os = exc.getResponseBody();
		os.write(response.getBytes());
		os.close();
		file = null;
		response = null;
		urlRoot = null;
		urlPath = null;
		requestedFile = null;
		encoded = null;
		exc = null;
	} 
}
