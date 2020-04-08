package com.fly.utils;

import cc.eguid.commandManager.CommandManager;
import cc.eguid.commandManager.CommandManagerImpl;

public class ComandManagerHolder {
	
	private static CommandManager manager;
	
	static {
		manager = new CommandManagerImpl();
	}
	
	public static CommandManager getCommandManager() {
		return manager;
	}

}
