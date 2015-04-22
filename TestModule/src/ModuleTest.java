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
import com.google.common.eventbus.Subscribe;
import net.petercashel.jmsDd.API.API;
import net.petercashel.jmsDd.event.module.*;
import net.petercashel.jmsDd.module.Module;

@Module(ModuleName = "TestModule")
public class ModuleTest {

	public ModuleTest() {
		System.out.println("This is where you'd call your code to instantiate anything. \n But dont subscribe this class to the eventbus. Its automatic."
				+ "\n Feel free to subscribe other event handling classes though.");
	}

	@Subscribe
	public static void loadConfig(ModuleConfigEvent e) {
		System.out.println("This is where you'd handle config");
	}

	@Subscribe
	public static void preInit(ModulePreInitEvent e) {
		System.out
				.println("This is where you'd init anything needed post Auth/Command init but before network (no packets)");
	}

	@Subscribe
	public static void init(ModuleInitEvent e) {
		System.out.println("This is where you'd init anything needed post Network and pre process start");
	}

	@Subscribe
	public static void postInit(ModulePostInitEvent e) {
		System.out.println("This is where you'd init anything needed after all core code is started");
	}

	@Subscribe
	public static void shutdown(ModuleShutdownEvent e) {
		System.out.println("And finally, Shut everything down.");
	}

}
