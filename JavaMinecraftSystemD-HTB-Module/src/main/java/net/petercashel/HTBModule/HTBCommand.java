package net.petercashel.HTBModule;

import net.petercashel.jmsDd.auth.interfaces.IAuthDataSystem.permissionLevels;
import net.petercashel.jmsDd.command.ICommand;
import net.petercashel.jmsDd.command.commandServer;

public class HTBCommand implements ICommand {

	@Override
	public String commandName() {
		return "htb";
	}

	@Override
	public boolean processCommand(String[] args) {
		if (args.length < 2) {
			help();
			return true;
		}
		if (args[1].equalsIgnoreCase("update")) {
			HTB_Module.doUpdate();
			return true;
		}
		return false;
	}

	private void help() {
		commandServer.out.println("help,update");
	}

	@Override
	public permissionLevels requiredPermissionLevel() {
		// TODO Auto-generated method stub
		return permissionLevels.MODERATOR;
	}


}
