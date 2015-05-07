
package net.md_5.bungee;

import java.util.concurrent.ConcurrentHashMap;

public class MessageAntiSpam {
	
	public ConcurrentHashMap<String, Long> alreadyConnecting = new ConcurrentHashMap<String, Long>();
	public ConcurrentHashMap<String, Long> alreadyConnected = new ConcurrentHashMap<String, Long>();
	
	public MessageAntiSpam() {}
	
	public boolean alreadyConnectingMsg(String playerName) {
		if (alreadyConnecting.contains(playerName)) {
			long last = alreadyConnecting.get(playerName);
			if ((this.now() - last) > 20) return false; 
			alreadyConnecting.put(playerName, this.now());
		}
		return true;
	}
	
	public boolean alreadyConnectedMsg(String playerName) {
		if (alreadyConnected.contains(playerName)) {
			long last = alreadyConnected.get(playerName);
			if ((this.now() - last) > 20) return false; 
			alreadyConnecting.put(playerName, this.now());
		}
		return true;
	}
	
	private long now() {
		return System.currentTimeMillis() / 1000L;
	}
}
