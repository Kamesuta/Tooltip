package net.teamfruit.visualink.addons.enderstorage;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import net.minecraft.tileentity.TileEntity;
import net.teamfruit.visualink.Reference;
import net.teamfruit.visualink.VisualinkBlocks;
import net.teamfruit.visualink.addons.IAccessor;
import net.teamfruit.visualink.addons.IdentifierProvider;

public class EnderStorageModule {
	public static Class<?> TileFrequencyOwner = null;
	public static Field TileFrequencyOwner_Freq = null;
	public static Field TileFrequencyOwner_Owner = null;
	public static Class<?> TileEnderTank = null;

	public static void register(final List<VisualinkBlocks> blocks) {
		try {
			Class.forName("codechicken.enderstorage.EnderStorage");
			Reference.logger.log(Level.INFO, "EnderStorage mod found.");
		} catch (final ClassNotFoundException arg4) {
			Reference.logger.log(Level.INFO, "[EnderStorage] EnderStorage mod not found.");
			return;
		}

		try {
			TileFrequencyOwner = Class.forName("codechicken.enderstorage.common.TileFrequencyOwner");
			TileFrequencyOwner_Freq = TileFrequencyOwner.getField("freq");
			TileFrequencyOwner_Owner = TileFrequencyOwner.getField("owner");

			TileEnderTank = Class.forName("codechicken.enderstorage.storage.liquid.TileEnderTank");
		} catch (final ClassNotFoundException arg0) {
			Reference.logger.log(Level.WARN, "[EnderStorage] Class not found. "+arg0);
			return;
		} catch (final NoSuchFieldException arg2) {
			Reference.logger.log(Level.WARN, "[EnderStorage] Field not found."+arg2);
			return;
		} catch (final Exception arg3) {
			Reference.logger.log(Level.WARN, "[EnderStorage] Unhandled exception."+arg3);
			return;
		}

		blocks.add(new VisualinkBlocks("EnderStorage:enderChest", new IdentifierProvider() {
			@Override
			public @Nullable String provide(final @Nonnull IAccessor accessor) {
				try {
					final TileEntity tile = accessor.getTileEntity();
					if (tile==null)
						return null;
					final int freq = TileFrequencyOwner_Freq.getInt(tile);
					final boolean isTank = TileEnderTank.isInstance(tile);
					final String owner = (String) TileFrequencyOwner_Owner.get(tile);
					return accessor.getBlockID()+"@"+(isTank ? "t" : "c")+freq+"@"+owner;
				} catch (final Exception arg8) {
				}
				return null;
			}
		}));
	}
}