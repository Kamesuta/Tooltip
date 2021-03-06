package net.teamfruit.visualink.addons.jabba;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.teamfruit.visualink.Log;
import net.teamfruit.visualink.network.IVisualinkMessage;
import net.teamfruit.visualink.network.VisualinkPacketHandler;

public class Message0x00UpdateBarrelLinks extends SimpleChannelInboundHandler<Message0x00UpdateBarrelLinks> implements IVisualinkMessage {
	public NBTTagCompound fullLinksTag = new NBTTagCompound();

	public Message0x00UpdateBarrelLinks() {
	}

	public Message0x00UpdateBarrelLinks(final BarrelLink barrel) {
		barrel.writeToNBT(this.fullLinksTag);
	}

	@Override
	public void encodeInto(final ChannelHandlerContext ctx, final IVisualinkMessage msg, final ByteBuf target) throws Exception {
		VisualinkPacketHandler.INSTANCE.writeNBTTagCompoundToBuffer(target, this.fullLinksTag);
	}

	@Override
	public void decodeInto(final ChannelHandlerContext ctx, final ByteBuf dat, final IVisualinkMessage rawmsg) {
		final Message0x00UpdateBarrelLinks msg = (Message0x00UpdateBarrelLinks) rawmsg;
		try {
			msg.fullLinksTag = VisualinkPacketHandler.INSTANCE.readNBTTagCompoundFromBuffer(dat);
		} catch (final Exception arg5) {
			;
		}
	}

	@Override
	protected void channelRead0(final ChannelHandlerContext ctx, final Message0x00UpdateBarrelLinks msg) throws Exception {
		Log.log.info("Received server barrel links msg.");
		final BarrelLink manager = BarrelLink.instance;
		if (manager!=null)
			manager.readFromNBT(msg.fullLinksTag);
	}
}