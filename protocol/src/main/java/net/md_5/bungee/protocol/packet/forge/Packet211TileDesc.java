
package net.md_5.bungee.protocol.packet.forge;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/** Created by thomas on 4/21/14. */

@ToString
@EqualsAndHashCode(callSuper = false)
public class Packet211TileDesc extends PacketVLC {
	
	private int size = 0;
	private int cnt1 = 0;
	public int zCoord;
	public int yCoord;
	public int xCoord;
	public int subId;
	
	public Packet211TileDesc() {
		super(0xD3);
	}
	
	@SuppressWarnings("resource")
	@Override
	public void read(ByteBuf buf) {
		try {
			ByteBufInputStream in = new ByteBufInputStream(buf);
			
			this.subId = in.read();
			if (this.subId == -1) { throw new IOException("Not enough data"); }
			this.xCoord = ((int) readVLC(in));
			this.yCoord = ((int) readVLC(in));
			this.zCoord = ((int) readVLC(in));
			int l = (int) readUVLC(in);
			if (l > 65535) { throw new IOException("Packet too big"); }
			this.size = (this.cnt1 + l + 1);
			byte[] body = new byte[l];
			int p = 0;
			while (l - p > 0) {
				int rl = in.read(body, p, l - p);
				if (rl < 1) { throw new IOException("Not enough data"); }
				if (p + rl >= l) {
					break;
				}
				p += rl;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
