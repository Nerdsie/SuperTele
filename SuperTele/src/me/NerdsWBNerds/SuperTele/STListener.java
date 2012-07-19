package me.NerdsWBNerds.SuperTele;

import static  org.bukkit.ChatColor.*;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
@Deprecated
public class STListener implements Listener{
	public SuperTele plugin;
	public STListener(SuperTele p){
		plugin = p;
	}
	//public boolean onCommand(CommandSender sender, Command cmd, String commandLevel, String[] args){ try using this
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		String[] args = e.getMessage().split(" ");

		if(!player.isOp()){
			tell(player, RED + "[SuperTele] You do not have permission to do this.");
			return;
		}

		if
		(
			args[0].equalsIgnoreCase("/from") || 
			args[0].equalsIgnoreCase("/here") ||
			args[0].equalsIgnoreCase("/bring") ||
			args[0].equalsIgnoreCase("/yank")
		)
		{
			if(player.hasPermission("supertele.from"))
			{
				if(args.length==2)
				{
					Player to = plugin.getServer().getPlayer(args[1]);
					
					if(to!=null && to.isOnline())
					{
						to.teleport(player);
						tell(to, GOLD + "[SuperTele]" + GREEN +" You have been teleported to " + AQUA + player.getName());
						e.setCancelled(true);
					}
					else
					{
						tell(player, RED + "[SuperTele] Player not found.");
						return;
					}
				}
				
				if(args.length!=2)
					return;
			}
			else
			{
				tell(player, RED + "[SuperTele] You do not have permission.");
				return;
			}
		}
		
		if(args[0].equalsIgnoreCase("/sttp"))
		{
			if(player.hasPermission("supertele.tp"))
			{
				// Player sending the command wants to tp to another player
				if(args.length==2)
				{
					Player to = plugin.getServer().getPlayer(args[1]);
				
					if(to!=null && to.isOnline())
					{
						player.teleport(to);
						tell(player, GOLD + "[SuperTele]" + GREEN +" You have been teleported to " + AQUA + to.getName());
						return;
					}
					else
					{
						tell(player, RED + "[SuperTele] Player not found.");
						return;
					}
				}

				// Teleport one player to another.
				if(args.length==3)
				{
					Player from = plugin.getServer().getPlayer(args[1]);
					Player to = plugin.getServer().getPlayer(args[2]);
					
					if(to!=null && to.isOnline())
					{
						if(from!=null && from.isOnline())
						{
							from.teleport(to);
							tell(from, GOLD + "[SuperTele]" + GREEN +" You have been teleported to " + AQUA + to.getName());
							return;
						}
						// The person to be teleported was not found.
						else
						{
							tell(player, RED + "[SuperTele] Player not found.");
							return;
						}
					}
					// The target player was not found.
					else
					{
						tell(player, RED + "[SuperTele] Player not found.");
						return;
					}				
				}

			}
		}
		
		if
		(
			args[0].equalsIgnoreCase("/tpall") ||
			args[0].equalsIgnoreCase("/tpa") ||
			args[0].equalsIgnoreCase("/allhere") ||
			args[0].equalsIgnoreCase("/tome")
		)
		{
			if(player.hasPermission("supertele.tpall"))
			{
				Player to = player;

				// Tping everyone to a target player
				if(args.length == 2)
				{
					e.setCancelled(true);
					to = plugin.getServer().getPlayer(args[1]);
					
					// Target Player Doesn't exist.
					if(to==null || !to.isOnline())
					{
						tell(player, RED + "[SuperTele] Player not found.");
						return;
					}
				}				

				// Handle TPing to the command sender or to another player.
				if(args.length == 1 || args.length == 2)
				{
					e.setCancelled(true);
					
					// Teleport everyone to the Player or the Target player (to)
					for(Player target: plugin.getServer().getOnlinePlayers())
					{
						if(target.getName().equalsIgnoreCase(to.getName()))
							continue;
						
						target.teleport(to);
						tell(target, GOLD + "[SuperTele]" + GREEN +" You have been teleported to " + AQUA + to.getName());
					}
				}
			}
			
			if(args[0].equalsIgnoreCase("/tpc") || args[0].equalsIgnoreCase("/sendto"))
			{
				if(player.hasPermission("supertele.tpc"))
				{
					Player target = player;
					int aOff = 0;

					// Teleport the target Player to the specified Coordinates.
					if(args.length==5)
					{
						e.setCancelled(true);
						
						target = plugin.getServer().getPlayer(args[1]);
						aOff = 1;
						
						if(target==null || !target.isOnline())
						{
							tell(player, RED + "[SuperTele] Player not found.");
							return;
						}
					}

					// Handles teleporting either the Player or the Target Player to the Specified Coordinates.
					if(args.length==4 || args.length==5)
					{
						Location to = new Location(target.getWorld(), toInt(args[1 + aOff]), toInt(args[2 + aOff]), toInt(args[3 + aOff]));
					
						tell(target, GOLD + "[SuperTele]" + GREEN +" You have been teleported to" + AQUA +" X:" + to.getX() + " Y:" + to.getY() + " Z:" + to.getZ());
						
						if(target!=player)
						{
							tell(player, GOLD + "[SuperTele] " + GREEN + "You have teleported " + AQUA + target.getName() + GREEN + " to"  + AQUA +" X:" + to.getX() + " Y:" + to.getY() + " Z:" + to.getZ());
						}

						target.teleport(to);
					}
				}
			}
			
			// Teleport yourself to coordinates or to another player.
			if(args[0].equalsIgnoreCase("/tpto") || args[0].equalsIgnoreCase("/goto"))
			{
				if(player.hasPermission("supertele.tpto"))
				{
					int aOff = 0;

					// Handles teleporting either the Player or the Target Player to the Specified Coordinates.
					if(args.length==4)
					{
						Location to = new Location(player.getWorld(), toInt(args[1 + aOff]), toInt(args[2 + aOff]), toInt(args[3 + aOff]));
					
						tell(player, GOLD + "[SuperTele]" + GREEN +" You have been teleported to" + AQUA +" X:" + to.getX() + " Y:" + to.getY() + " Z:" + to.getZ());

						player.teleport(to);
					}
					
					if(args.length == 2)
					{
						Player to = plugin.getServer().getPlayer(args[1]);
						
						if(to != null && to.isOnline())
						{
							player.teleport(to);
							tell(player, GOLD + "[SuperTele]" + GREEN +" You have been teleported to " + AQUA + to.getName());
							return;
						}
						else
						{
							tell(player, RED + "[SuperTele] Player not found.");
							return;
						}						
					}
				}
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
