package net.md_5.bungee.protocol51.skip;

import io.netty.buffer.ByteBuf;

class UnsignedShortByte extends Instruction
{

    @Override
    void read(ByteBuf in)
    {
        int size = in.readUnsignedShort();
        in.skipBytes( size );
    }
}
