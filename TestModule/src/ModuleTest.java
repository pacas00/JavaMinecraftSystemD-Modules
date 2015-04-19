import com.google.common.eventbus.Subscribe;

import net.petercashel.jmsDd.daemonMain;
import net.petercashel.jmsDd.event.*;
import net.petercashel.jmsDd.module.core.Module;


@Module(ModuleName = "TestModule")
public class ModuleTest {
	
	public ModuleTest() {
		System.out.println("This is where you'd call your code to subscribe to events");
		daemonMain.eventBus.register(this);
	}
	
	@Subscribe
	public static void loadConfig(ModuleConfigEvent e) {
		System.out.println("This is where you'd handle config");	
	}
	
	@Subscribe
	public static void preInit(ModulePreInitEvent e) {
		System.out.println("This is where you'd init anything needed post Auth/Command init but before network (no packets)");	
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
