
package net.md_5.bungee.protocol.packet;

import io.netty.buffer.ByteBuf;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PacketFAPluginMessage extends DefinedPacket {
	
	private String tag;
	private byte[] data;
	
	private PacketFAPluginMessage() {
		super(0xFA);
	}
	
	public PacketFAPluginMessage(String tag, byte[] data) {
		this();
		this.tag = tag;
		this.data = data;
	}
	
	@Override
	public void read(ByteBuf buf) {
		tag = readString(buf);
		data = readArray(buf);
	}
	
	@Override
	public void write(ByteBuf buf) {
		writeString(tag, buf);
		writeArray(data, buf);
	}
	
	@Override
	public void handle(AbstractPacketHandler handler) throws Exception {
		handler.handle(this);
	}
}
