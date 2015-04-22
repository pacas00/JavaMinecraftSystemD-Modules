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

import java.io.IOException;
import java.net.InetSocketAddress;
import net.petercashel.jmsDd.API.API;
import net.petercashel.jmsDd.event.module.EventBase;
import net.petercashel.jmsDd.event.module.ModuleConfigEvent;
import net.petercashel.jmsDd.event.module.ModuleInitEvent;
import net.petercashel.jmsDd.event.module.ModulePostInitEvent;
import net.petercashel.jmsDd.event.module.ModulePreInitEvent;
import net.petercashel.jmsDd.event.module.ModuleShutdownEvent;
import net.petercashel.jmsDd.module.Module;
import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;

@Module(ModuleName = "WebAPIModule")
@SuppressWarnings("restriction")
public class moduleWebAPI {
	static String ModuleName = "WebAPIModule";

	public static com.sun.net.httpserver.HttpServer server;
	static InetSocketAddress socketAddress;
	static int port = 0;

	public moduleWebAPI(int port) {
		//Dummy useless constructor, left in to throw warnings intentionally.
		// Warning as in, WARNING! THIS CRAP IS BEING LOADED!
	}

	@Subscribe
	public static void loadConfig(ModuleConfigEvent e) {
		JsonObject cfgRoot = e.apiInstance.getConfigJSONObject(e._cfg, ModuleName);
		port = e.apiInstance.getConfigDefault(cfgRoot, "APIPort", 14480);
		socketAddress = new InetSocketAddress(port);
	}

	@Subscribe
	public static void preInit(ModulePreInitEvent e) {
		try {
			server = HttpServer.create(socketAddress, 0);
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		server.createContext("/", new DefaultHandler());
	}

	@Subscribe
	public static void init(ModuleInitEvent e) {
		
		
	}

	@Subscribe
	public static void postInit(ModulePostInitEvent e) {
		server.setExecutor(null);
		server.start();
		System.out.println("created server on port " + port);
	}

	@Subscribe
	public static void shutdown(ModuleShutdownEvent e) {
		server.stop(0);
	}
}
