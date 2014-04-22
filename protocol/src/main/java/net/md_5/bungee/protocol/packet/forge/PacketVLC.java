package net.md_5.bungee.protocol.packet.forge;

import io.netty.buffer.ByteBuf;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.md_5.bungee.protocol.packet.AbstractPacketHandler;
import net.md_5.bungee.protocol.packet.DefinedPacket;

import java.io.*;

/**
 * Created by thomas on 4/21/14.
 */

@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class PacketVLC extends DefinedPacket {

    public PacketVLC(int id)
    {
        super(id);
        this.headout = new ByteArrayOutputStream();
        this.bodyout = new ByteArrayOutputStream();

    }

    public byte[] toByteArray()
    {
        try
        {
            this.bodyout.writeTo(this.headout);
        }
        catch (IOException e) {}
        return this.headout.toByteArray();
    }

    public void a(DataOutputStream out)
            throws IOException
    {
        this.headout.writeTo(out);
        this.bodyout.writeTo(out);
    }

    public void addByte(int b)
    {
        this.bodyout.write(b);
    }

    public void addUVLC(long l)
    {
        writeUVLC(this.bodyout, l);
    }

    public void addVLC(long l)
    {
        writeVLC(this.bodyout, l);
    }

    public void addByteArray(byte[] ba)
    {
        addUVLC(ba.length);
        this.bodyout.write(ba, 0, ba.length);
    }

    public int getByte()
            throws IOException
    {
        int i = this.bodyin.read();
        if (i < 0) {
            throw new IOException("Not enough data");
        }
        return i;
    }

    public long getUVLC()
            throws IOException
    {
        return readUVLC(this.bodyin);
    }

    public long getVLC()
            throws IOException
    {
        return readVLC(this.bodyin);
    }

    public byte[] getByteArray()
            throws IOException
    {
        int ln = (int)getUVLC();
        byte[] tr = new byte[ln];
        this.bodyin.read(tr, 0, ln);
        return tr;
    }

    protected static void writeVLC(ByteArrayOutputStream os, long l)
    {
        if (l >= 0L) {
            l <<= 1;
        } else {
            l = -l << 1 | 1L;
        }
        writeUVLC(os, l);
    }

    protected static void writeUVLC(ByteArrayOutputStream os, long l)
    {
        do
        {
            int i = (int)(l & 0x7F);l >>>= 7;
            if (l != 0L) {
                i |= 0x80;
            }
            os.write(i);
        } while (l != 0L);
    }

    protected long readUVLC(InputStream in)
            throws IOException
    {
        long tr = 0L;
        int sc = 0;
        do
        {
            int i = in.read();
            if (i < 0) {
                throw new IOException("Not enough data");
            }
            this.cnt1 += 1;
            tr |= (i & 0x7F) << sc;
            if ((i & 0x80) == 0) {
                break;
            }
            sc += 7;
        } while (sc <= 64);
        return tr;
    }

    protected long readVLC(InputStream in)
            throws IOException
    {
        long tr = readUVLC(in);
        if ((tr & 1L) == 0L) {
            tr >>>= 1;
        } else {
            tr = -(tr >>> 1);
        }
        return tr;
    }

    public int a()
    {
        return this.size;
    }

    protected void fixLocalPacket()
    {
        this.bodyin = new ByteArrayInputStream(this.bodyout.toByteArray());
    }

    protected int size = 0;
    protected int cnt1 = 0;
    public ByteArrayOutputStream bodyout = null;
    public ByteArrayOutputStream headout = null;
    public ByteArrayInputStream bodyin = null;

    @Override
    public void read(ByteBuf buf) {

    }

    @Override
    public void write(ByteBuf buf) {

    }

    @Override
    public void handle(AbstractPacketHandler handler) throws Exception {

    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public abstract String toString();
}
