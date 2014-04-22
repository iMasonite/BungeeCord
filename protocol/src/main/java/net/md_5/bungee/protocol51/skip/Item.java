package net.md_5.bungee.protocol51.skip;

import io.netty.buffer.ByteBuf;

class Item extends Instruction
{

    @Override
    void read(ByteBuf in)
    {
        short type = in.readShort();
        if ( type >= 0 )
        {
            in.skipBytes( 3 );
            SHORT_BYTE.read( in );
        }
    }
}
