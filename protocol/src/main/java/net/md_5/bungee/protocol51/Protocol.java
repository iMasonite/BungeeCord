package net.md_5.bungee.protocol51;

import io.netty.buffer.ByteBuf;
import java.lang.reflect.Constructor;
import net.md_5.bungee.protocol51.packet.DefinedPacket;
import net.md_5.bungee.protocol51.skip.PacketReader;

public interface Protocol
{

    PacketReader getSkipper();

    DefinedPacket read(short packetId, ByteBuf buf);

    OpCode[][] getOpCodes();

    Class<? extends DefinedPacket>[] getClasses();

    Constructor<? extends DefinedPacket>[] getConstructors();
}
