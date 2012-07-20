package me.NerdsWBNerds.SuperTele;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;


public class SuperTele extends JavaPlugin implements Listener 
{
	private STCommandExecutor cmdEx;
	
	public void onEnable(){
		
		cmdEx = new STCommandExecutor(this);
		getCommand("sttp").setExecutor(cmdEx);
		
		getCommand("tpc").setExecutor(cmdEx);
		getCommand("sendto").setExecutor(cmdEx);
		
		getCommand("tpall").setExecutor(cmdEx);
		getCommand("tpa").setExecutor(cmdEx);
		getCommand("allhere").setExecutor(cmdEx);
		getCommand("tome").setExecutor(cmdEx);
		
		getCommand("tpto").setExecutor(cmdEx);
		getCommand("goto").setExecutor(cmdEx);
		
		getCommand("from").setExecutor(cmdEx);
		getCommand("here").setExecutor(cmdEx);
		getCommand("yank").setExecutor(cmdEx);
		getCommand("bring").setExecutor(cmdEx);
		
		this.getServer().getPluginManager().registerEvents(this, this);

	}
	
	public void onDisable(){
		
	}
	
	
}
