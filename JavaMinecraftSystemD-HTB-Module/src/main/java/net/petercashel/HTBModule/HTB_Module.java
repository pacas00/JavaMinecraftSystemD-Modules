package net.petercashel.HTBModule;
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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import com.google.common.eventbus.Subscribe;
import net.petercashel.jmsDd.Configuration;
import net.petercashel.jmsDd.API.API;
import net.petercashel.jmsDd.event.module.*;
import net.petercashel.jmsDd.event.process.ProcessPreRestartEvent;
import net.petercashel.jmsDd.module.Module;

@Module(ModuleName = "HTB_Module")
public class HTB_Module {

	@Subscribe
	public static void loadConfig(ModuleConfigEvent e) {
		//TXT Record for version JSON serverversionjson.petercashel.net
		//TXT Record for installer launcher.petercashel.net
	}

	@Subscribe
	public static void preInit(ModulePreInitEvent e) {
		
	}

	@Subscribe
	public static void init(ModuleInitEvent e) {
		
	}

	@Subscribe
	public static void postInit(ModulePostInitEvent e) {
		
	}

	@Subscribe
	public static void shutdown(ModuleShutdownEvent e) {
		
	}


	@Subscribe
	public static void HandleUpdate(ProcessPreRestartEvent e) {
		doUpdate();
	}
	
	// Functions for be do stuff
	
	public static String getTxtRecord(String hostName) {
	    // Get the first TXT record

	    java.util.Hashtable<String, String> env = new java.util.Hashtable<String, String>();	
	    env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

	    try {
	        javax.naming.directory.DirContext dirContext 
	            = new javax.naming.directory.InitialDirContext(env);	
	        javax.naming.directory.Attributes attrs 
	            = dirContext.getAttributes(hostName, new String[] { "TXT" });
	        javax.naming.directory.Attribute attr 
	            = attrs.get("TXT");

	        String txtRecord = "";

	        if(attr != null) {
	            txtRecord = attr.get().toString();
	        }

	        return txtRecord;

	    } catch (javax.naming.NamingException e) {

	        e.printStackTrace();
	        return "";
	    }
	}
	
	public static void downloadFile(String url, String dir, String filename) {
        try {
            URL URL;
            URL = new URL(url);
            File File = new File(dir + filename);
            org.apache.commons.io.FileUtils.copyURLToFile(URL, File);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Error Downloading " + filename);
        }
    }
	
    private static void doUpdate() {
    	
        println("********************");
        println("********************");
        println("********************");
        println("Mod Updater Starting");
        println("********************");
        println("********************");
        println("********************");

        File installDir = new File(API.Impl.getAPI().getConfigDefault(API.Impl.getAPI().getConfigJSONObject(Configuration.cfg, "processSettings"), "processWorkingDirectory", ""));
        // Installer functions as it normally does, except it gets called via this method and not the loader

        // This writes path.txt so the code in the installer can find the right directory
        try {
            // Might need to be changed to OS_Util.getWorkingDirectory() depending on what dir it works in.
            FileUtils.writeStringToFile( new File(installDir, "path.txt"), installDir.getCanonicalPath() + File.separator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Download installer as library

        File Installfile = null;
        try {
            Installfile = new File(installDir.getCanonicalPath() + File.separator + "htb3-installer.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!Installfile.exists()) {
            try {
                URL uRL;
                uRL = new URL(getTxtRecord("launcher.petercashel.net"));
                org.apache.commons.io.FileUtils.copyURLToFile(uRL, Installfile);
            } catch (IOException e) {
                // Auto-generated catch block
                e.printStackTrace();
                System.err.println("Error Downloading " + "htb3-installer.jar");
            }
        } else {
            Installfile.delete();
            try {
                URL uRL;
                uRL = new URL(getTxtRecord("launcher.petercashel.net"));
                org.apache.commons.io.FileUtils.copyURLToFile(uRL, Installfile);
            } catch (IOException e) {
                // Auto-generated catch block
                e.printStackTrace();
                System.err.println("Error Downloading " + "htb3-installer.jar");
            }
        }

        boolean added = false;
        if (Installfile.exists()) {
            API.Impl.getAPI().LoadJar(Installfile);
			added = true;
        }

        if (added) {
            // Call HTB-Installer via its loader
            String[] arg = new String[1];
            arg[0] = "--installServer";
            try {
                Class<?> clazz = Class.forName("net.petercashel.launch.Loader");
                Method m = clazz.getMethod("main", String[].class);
                m.invoke(null, (Object)arg);
            } catch (ClassNotFoundException e) {
                // Auto-generated catch block
                e.printStackTrace();
                System.out.println("NO UPDATER");
                System.out.println("Cannot Continue");
            } catch (NoSuchMethodException e) {
                // Auto-generated catch block
                e.printStackTrace();
                System.out.println("Cannot Continue");
            } catch (SecurityException e) {
                //  Auto-generated catch block
                e.printStackTrace();
                System.out.println("Cannot Continue");
            } catch (IllegalAccessException e) {
                //  Auto-generated catch block
                e.printStackTrace();
                System.out.println("Cannot Continue");
            } catch (IllegalArgumentException e) {
                //  Auto-generated catch block
                e.printStackTrace();
                System.out.println("Cannot Continue");
            } catch (InvocationTargetException e) {
                //  Auto-generated catch block
                e.printStackTrace();
                System.out.println("Cannot Continue");
            }
        }

        println("********************");
        println("********************");
        println("********************");
        println("Mod Updater Complete");
        println("********************");
        println("********************");
        println("********************");

    }

	private static void println(String string) {
		System.out.println(string);
		API.Impl.getAPI().OutputToClient().println(string);
		
	}
	
    
	

}
