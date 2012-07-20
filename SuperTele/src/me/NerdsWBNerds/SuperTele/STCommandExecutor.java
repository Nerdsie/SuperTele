package me.NerdsWBNerds.SuperTele;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class STCommandExecutor implements CommandExecutor
{
	private SuperTele	plugin;
	private Server		server;
	private Logger		logger;

	public STCommandExecutor(SuperTele p)
	{
		this.plugin = p;
		this.server = plugin.getServer();
		this.logger = plugin.getLogger();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = null;
		//String cmdName = cmd.getName();

		if (sender instanceof Player)
		{
			player = (Player) sender;
		} 
		else
		{
			this.logger.info("This command can not be run from the console.");
			return true;
		}

		if 
		(
			cmd.getName().equalsIgnoreCase("from") 	|| 
			cmd.getName().equalsIgnoreCase("here") 	|| 
			cmd.getName().equalsIgnoreCase("bring")	|| 
			cmd.getName().equalsIgnoreCase("yank")
		)
		{
			if (player.hasPermission("supertele.from"))
			{
				if (args.length == 1)
				{
					Player to = this.server.getPlayer(args[0]);

					if (  to != null && to.isOnline())
					{
						to.teleport(player);
						tell
						(
							to, 
							ChatColor.GOLD 	+ "[SuperTele]" + 
							ChatColor.GREEN + " You have been teleported to " + 
							ChatColor.AQUA + player.getName()
						);
						return true;
					}

					tell(player, ChatColor.RED	+ "[SuperTele] Player not found.");
					return true;
				}

				if (args.length != 1)
				{
					tell(player, ChatColor.RED	+ "[SuperTele] Wrong number of Arguments.");
					return false;
				}
			} else
			{
				tell(player, ChatColor.RED + "[SuperTele] You do not have permission.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("sttp"))
		{
			if (player.hasPermission("supertele.tp"))
			{
				if (args.length == 1)
				{
					Player to = this.server.getPlayer(args[0]);

					if (to != null && to.isOnline())
					{
						player.teleport(to);
						tell
						(
							player, 
							ChatColor.GOLD 	+ "[SuperTele]" + 
							ChatColor.GREEN	+ " You have been teleported to " + 
							ChatColor.AQUA + to.getName()
						);
						return true;
					}

					tell(player, ChatColor.RED + "[SuperTele] Player not found.");
					return true;
				}

				if (args.length == 2)
				{
					Player from = this.server.getPlayer(args[0]);
					Player to = this.server.getPlayer(args[1]);

					if (to != null && to.isOnline())
					{
						if (from != null && from.isOnline())
						{
							from.teleport(to);
							tell
							(
								from, 
								ChatColor.GOLD 	+ "[SuperTele]" + 
								ChatColor.GREEN + " You have been teleported to " + 
								ChatColor.AQUA 	+ 
								to.getName()
							);
							return true;
						}

						tell(player, ChatColor.RED + "[SuperTele] Player not found.");
						return true;
					}

					tell(player, ChatColor.RED + "[SuperTele] Player not found.");
					return true;
				}

				tell(player, ChatColor.RED + "[SuperTele] Wrong number of Arguments.");
				return false;
			}

		}

		if 
		(
			cmd.getName().equalsIgnoreCase("tpall")		|| 
			cmd.getName().equalsIgnoreCase("tpa")		||
			cmd.getName().equalsIgnoreCase("allhere")	|| 
			cmd.getName().equalsIgnoreCase("tome")
		)
		{
			if (player.hasPermission("supertele.tpall"))
			{
				Player to = player;

				if (args.length == 1)
				{
					to = this.server.getPlayer(args[0]);

					if (to == null || !to.isOnline())
					{
						tell(player, ChatColor.RED + "[SuperTele] Player not found.");
						return true;
					}

				}

				if ((args.length == 0) || (args.length == 1))
				{
					if (this.server.getOnlinePlayers().length == 1)
					{
						tell(player, ChatColor.RED + "[SuperTele] Nobody else is on, to whom were you going to Teleport?");
						return true;
					}

					for (Player target : this.server.getOnlinePlayers())
					{
						if (target.getName().equalsIgnoreCase(to.getName()))
						{
							continue;
						}
						target.teleport(to);
						tell
						(
							target, 
							ChatColor.GOLD 	+ "[SuperTele]" + 
							ChatColor.GREEN + " You have been teleported to " + 
							ChatColor.AQUA 	+ to.getName()
						);
					}
					return true;
				}

				tell(player, ChatColor.RED	+ "[SuperTele] Wrong number of Arguments.");
				return false;
			}

		}

		if (cmd.getName().equalsIgnoreCase("tpc") || cmd.getName().equalsIgnoreCase("sendto"))
		{
			if (player.hasPermission("supertele.tpc"))
			{
				Player target = player;
				int aOff = 0;

				if (args.length == 4)
				{
					target = this.server.getPlayer(args[0]);
					aOff = 1;

					if ( (target == null) || (!target.isOnline()))
					{
						tell(player, ChatColor.RED	+ "[SuperTele] Player not found.");
						return true;
					}

				}

				if ((args.length == 3) || (args.length == 4))
				{
					Location to = 	new Location
									(
										target.getWorld(),
										toInt(args[(0 + aOff)]), 
										toInt(args[(1 + aOff)]),
										toInt(args[(2 + aOff)])
									);

					tell
					(
						target, 
						ChatColor.GOLD 	+ "[SuperTele]" + 
						ChatColor.GREEN	+ " You have been teleported to" + 
						ChatColor.AQUA 	+ " X:" + to.getX()	+ " Y:" + to.getY() + " Z:" + to.getZ()
					);

					if (target != player)
					{
						tell
						(
							player, 
							ChatColor.GOLD 	+ "[SuperTele] " + 
							ChatColor.GREEN + "You have teleported " +
							ChatColor.AQUA 	+ target.getName() +
							ChatColor.GREEN + " to" + 
							ChatColor.AQUA 	+ " X:" + to.getX() + " Y:" + to.getY() + " Z:" + to.getZ()
						);
					}

					target.teleport(to);
					return true;
				}

				tell(player, ChatColor.RED + "[SuperTele] Wrong number of Arguments.");
				return false;
			}

		}

		if (cmd.getName().equalsIgnoreCase("tpto") || (cmd.getName().equalsIgnoreCase("goto")))
		{
			if (player.hasPermission("supertele.tpto"))
			{
				int aOff = 0;

				if (args.length == 3)
				{
					Location to = 	new Location
									(
										player.getWorld(), 
										toInt(args[(0 + aOff)]), 
										toInt(args[(1 + aOff)]),
										toInt(args[(2 + aOff)])
									);

					tell
					(
						player,
						ChatColor.GOLD 	+ "[SuperTele]" + 
						ChatColor.GREEN + " You have been teleported to" + 
						ChatColor.AQUA 	+ " X:" + to.getX() + " Y:" + to.getY() + " Z:" + to.getZ()
					);

					player.teleport(to);
					return true;
				} 
				else
				{
					if (args.length == 1)
					{
						Player to = this.server.getPlayer(args[0]);

						if ( to != null && to.isOnline())
						{
							player.teleport(to);
							tell
							(
								player, 
								ChatColor.GOLD 	+ "[SuperTele]" + 
								ChatColor.GREEN + " You have been teleported to " + 
								ChatColor.AQUA 	+ to.getName()
							);
							return true;
						}

						tell(player, ChatColor.RED + "[SuperTele] Player not found.");
						return true;
					}

					tell(player, ChatColor.RED + "[SuperTele] Wrong number of Arguments.");
					return false;
				}
			}
		}

		tell(player, ChatColor.RED + "[SuperTele] You do not have permission.");
		return true;
	}

	public void tell(Player p, String m)
	{
		p.sendMessage(m);
	}

	public int toInt(String i)
	{
		return Integer.parseInt(i);
	}
}