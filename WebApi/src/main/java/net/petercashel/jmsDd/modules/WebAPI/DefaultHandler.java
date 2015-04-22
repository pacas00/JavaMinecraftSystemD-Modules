/*******************************************************************************
 *    Copyright 2015 Peter Cashel (pacas00@petercashel.net)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/

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
import net.petercashel.jmsDd.API.API;
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

		File file = new File(new File(API.Impl.getAPI().getConfigDir(), "www_data"), requestedFile);
		byte[] encoded;
		if (file.exists()) {
			encoded = Files.readAllBytes(file.toPath());
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
