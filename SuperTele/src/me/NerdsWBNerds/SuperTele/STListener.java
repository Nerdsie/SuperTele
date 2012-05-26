package me.NerdsWBNerds.SuperTele;

import static  org.bukkit.ChatColor.*;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class STListener implements Listener{
	public SuperTele plugin;
	public STListener(SuperTele p){
		plugin = p;
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		String[] args = e.getMessage().split(" ");

		if(!player.isOp()){
			tell(player, RED + "[SuperTele] You do not have permission to do this.");
			return;
		}
		
		if(args[0].equalsIgnoreCase("/from") || args[0].equalsIgnoreCase("/here")){
			if(args.length==2){
				Player to = plugin.server.getPlayer(args[1]);
				if(to!=null && to.isOnline()){
					to.teleport(player);
					tell(to, GOLD + "[SuperTele]" + GREEN +" You have been teleported to " + AQUA + player.getName());
				}else{
					tell(player, RED + "[SuperTele] Player not found.");
					return;
				}
			}
			if(args.length!=2)
				return;
			
			e.setCancelled(true);
		}
		if(args[0].equalsIgnoreCase("/tp")){
			if(args.length==2){
				Player to = plugin.server.getPlayer(args[1]);
				if(to!=null && to.isOnline()){
					player.teleport(to);
					tell(player, GOLD + "[SuperTele]" + GREEN +" You have been teleported to " + AQUA + to.getName());
				}else{
					tell(player, RED + "[SuperTele] Player not found.");
					return;
				}
			}
			
			if(args.length==3){
				Player from = plugin.server.getPlayer(args[1]);
				Player to = plugin.server.getPlayer(args[2]);
				if(to!=null && to.isOnline()){
					if(to!=null && to.isOnline()){
						from.teleport(to);
						tell(from, GOLD + "[SuperTele]" + GREEN +" You have been teleported to " + AQUA + to.getName());
					}else{
						tell(player, RED + "[SuperTele] Player not found.");
						return;
					}
				}else{
					tell(player, RED + "[SuperTele] Player not found.");
					return;
				}				
			}
		}
		if(args[0].equalsIgnoreCase("/tpall")){
			Player to = player;
			
			if(args.length == 2){
				e.setCancelled(true);
				to = plugin.server.getPlayer(args[1]);
				if(to==null || !to.isOnline()){
					tell(player, RED + "[SuperTele] Player not found.");
					return;
				}
			}				
			
			if(args.length == 1 || args.length == 2){
				e.setCancelled(true);
				
				for(Player target: plugin.server.getOnlinePlayers()){
					target.teleport(to);
					tell(target, GOLD + "[SuperTele]" + GREEN +" You have been teleported to " + AQUA + to.getName());
				}
			}
		}
		if(args[0].equalsIgnoreCase("/tpc")){
			Player target = player;
			int aOff = 0;
			
			if(args.length==5){
				e.setCancelled(true);
				target = plugin.server.getPlayer(args[1]);
				aOff = 1;
				if(target==null || !target.isOnline()){
					tell(player, RED + "[SuperTele] Player not found.");
					return;
				}
			}
			
			if(args.length==4 || args.length==5){
				Location to = new Location(target.getWorld(), toInt(args[1 + aOff]), toInt(args[2 + aOff]), toInt(args[3 + aOff]));
				tell(target, GOLD + "[SuperTele]" + GREEN +" You have been teleported to" + AQUA +" X:" + to.getX() + " Y:" + to.getY() + " Z:" + to.getZ());
				if(target!=player){
					tell(player, GOLD + "[SuperTele] " + GREEN + "You have teleported " + AQUA + target.getName() + GREEN + " to"  + AQUA +" X:" + to.getX() + " Y:" + to.getY() + " Z:" + to.getZ());
				}
				target.teleport(to);
			}
		}
	}
	
	public void tell(Player p, String m){
		p.sendMessage(m);
	}
	
	public int toInt(String i){
		return Integer.parseInt(i);
	}
}
