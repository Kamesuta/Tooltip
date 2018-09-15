package mcp.mobius.waila.addons.enderstorage;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HUDHandlerStorage implements IWailaDataProvider {
	private static String[] colors = new String[] { LangUtil.translateG("hud.msg.white", new Object[0]), LangUtil.translateG("hud.msg.orange", new Object[0]), LangUtil.translateG("hud.msg.magenta", new Object[0]), LangUtil.translateG("hud.msg.lblue", new Object[0]), LangUtil.translateG("hud.msg.yellow", new Object[0]), LangUtil.translateG("hud.msg.lime", new Object[0]), LangUtil.translateG("hud.msg.pink", new Object[0]), LangUtil.translateG("hud.msg.gray", new Object[0]),
			LangUtil.translateG("hud.msg.lgray", new Object[0]), LangUtil.translateG("hud.msg.cyan", new Object[0]), LangUtil.translateG("hud.msg.purple", new Object[0]), LangUtil.translateG("hud.msg.blue", new Object[0]), LangUtil.translateG("hud.msg.brown", new Object[0]), LangUtil.translateG("hud.msg.green", new Object[0]), LangUtil.translateG("hud.msg.red", new Object[0]), LangUtil.translateG("hud.msg.black", new Object[0]) };

	@Override
	public ItemStack getWailaStack(final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(final ItemStack itemStack, List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
		if (config.getConfig("enderstorage.colors"))

			try {
				final int e = EnderStorageModule.TileFrequencyOwner_Freq.getInt(accessor.getTileEntity());
				final int freqLeft = ((Integer) EnderStorageModule.GetColourFromFreq.invoke((Object) null, new Object[] { Integer.valueOf(e), Integer.valueOf(0) })).intValue();
				final int freqCenter = ((Integer) EnderStorageModule.GetColourFromFreq.invoke((Object) null, new Object[] { Integer.valueOf(e), Integer.valueOf(1) })).intValue();
				final int freqRight = ((Integer) EnderStorageModule.GetColourFromFreq.invoke((Object) null, new Object[] { Integer.valueOf(e), Integer.valueOf(2) })).intValue();
				final String o = (String) EnderStorageModule.TileFrequencyOwner_Owner.get(accessor.getTileEntity());

				if (!EnderStorageModule.TileEnderTank.isInstance(accessor.getTileEntity()))
					currenttip.add(String.format("%s/%s/%s", new Object[] { colors[freqLeft], colors[freqCenter], colors[freqRight] }));

				else
					currenttip.add(String.format("%s/%s/%s", new Object[] { colors[freqRight], colors[freqCenter], colors[freqLeft] }));

				currenttip.add(String.format("Owner: %s", o));

			} catch (final Exception arg8) {
				currenttip = WailaExceptionHandler.handleErr(arg8, accessor.getTileEntity().getClass().getName(), currenttip);

			}

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(final EntityPlayerMP player, final TileEntity te, final NBTTagCompound tag, final World world, final int x, final int y, final int z) {
		if (te!=null)
			te.writeToNBT(tag);
		return tag;
	}
}