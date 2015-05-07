
package net.md_5.bungee.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/** Command to list and switch a player between available servers. */
public class CommandServer extends Command {
	
	public CommandServer() {
		super("server", "bungeecord.command.server");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) { return; }
		ProxiedPlayer player = (ProxiedPlayer) sender;
		Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();
		if (args.length == 0) {
			// player.sendMessage(ProxyServer.getInstance().getTranslation("current_server") +
			// player.getServer().getInfo().getName());
			
			List<String> toSend = new ArrayList<String>();
			
			toSend.add(ChatColor.translateAlternateColorCodes('&', "&7 [&3Keneti&7] &r&6Server Network."));
			for (ServerInfo server : servers.values()) {
				if (server.canAccess(player)) {
					toSend.add(ChatColor.translateAlternateColorCodes('&', "&a  " + '\u24BE' + "  &a" + Character.toUpperCase(server.getName().charAt(0)) + server.getName().substring(1) + "  &f|  &ePlayers: " + server.getPlayers().size()));
				}
				else {
					toSend.add(ChatColor.translateAlternateColorCodes('&', "&c  " + '\u24CD' + "  &a" + Character.toUpperCase(server.getName().charAt(0)) + server.getName().substring(1) + "  &f|  &ePlayers: " + server.getPlayers().size()));
				}
			}
			// StringBuilder serverList = new StringBuilder();
			// for (ServerInfo server : servers.values()) {
			// if (server.canAccess(player)) {
			// serverList.append(server.getName());
			// serverList.append(", ");
			// }
			// }
			// if (serverList.length() != 0) {
			// serverList.setLength(serverList.length() - 2);
			// }
			Collections.sort(toSend);
			player.sendMessage("");
			for (String string : toSend) {
				player.sendMessage(string);
			}
			player.sendMessage("");
		}
		else {
			ServerInfo server = servers.get(args[0]);
			if (server == null) {
				player.sendMessage(ProxyServer.getInstance().getTranslation("no_server"));
			}
			else if (!server.canAccess(player)) {
				player.sendMessage(ProxyServer.getInstance().getTranslation("no_server_permission"));
			}
			else {
				player.connect(server);
			}
		}
	}
}
