package net.md_5.bungee.protocol51.packet.forge;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.IOException;

/**
 * Created by thomas on 4/21/14.
 */

@ToString
@EqualsAndHashCode(callSuper = false)
public class Packet212GuiEvent extends PacketVLC {

    private int size = 0;
    private int cnt1 = 0;
    public int windowId;
    public int eventId;

    public Packet212GuiEvent() {
        super(0xD4);
    }

    @Override
    public void read(ByteBuf buf) {
        try {
            ByteBufInputStream in = new ByteBufInputStream(buf);

            this.windowId = in.read();
            if (this.windowId == -1) {
                throw new IOException("Not enough data");
            }
            this.eventId = ((int)readUVLC(in));
            int l = (int)readUVLC(in);
            if (l > 65535) {
                throw new IOException("Packet too big");
            }
            this.size = (this.cnt1 + l + 1);
            byte[] body = new byte[l];
            int p = 0;
            while (l - p > 0)
            {
                int rl = in.read(body, p, l - p);
                if (rl < 1) {
                    throw new IOException("Not enough data");
                }
                if (p + rl >= l) {
                    break;
                }
                p += rl;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
