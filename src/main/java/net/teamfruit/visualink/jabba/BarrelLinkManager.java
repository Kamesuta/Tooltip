package net.teamfruit.visualink.jabba;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Maps;

import mcp.mobius.betterbarrels.bspace.BSpaceStorageHandler;
import net.minecraft.nbt.NBTTagCompound;

public class BarrelLinkManager {
	public static final BarrelLinkManager instance = new BarrelLinkManager();

	public HashMap<Integer, HashSet<Integer>> links = Maps.newHashMap();

	public void readFromNBT(final NBTTagCompound nbt) {
		try {
			final Field field = BSpaceStorageHandler.class.getDeclaredField("links");
			field.setAccessible(true);

			if (nbt.hasKey("links")) {
				final NBTTagCompound tag = nbt.getCompoundTag("links");
				final Iterator<?> arg2 = tag.func_150296_c().iterator();
				while (arg2.hasNext()) {
					final Object key = arg2.next();
					this.links.put(Integer.valueOf((String) key), convertHashSet(tag.getIntArray((String) key)));
				}
			}
		} catch (final Exception e) {
		}
	}

	public void writeToNBT(final NBTTagCompound nbt) {
		try {
			final Field field = BSpaceStorageHandler.class.getDeclaredField("links");
			field.setAccessible(true);
			@SuppressWarnings("unchecked")
			final HashMap<Integer, HashSet<Integer>> links = (HashMap<Integer, HashSet<Integer>>) field.get(BSpaceStorageHandler.instance());

			final NBTTagCompound list2 = new NBTTagCompound();
			for (final Integer key1 : links.keySet())
				list2.setIntArray(String.valueOf(key1), convertInts(links.get(key1)));
			nbt.setTag("links", list2);
		} catch (final Exception e) {
		}
	}

	private int[] convertInts(final Set<Integer> integers) {
		final int[] ret = new int[integers.size()];
		final Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i<ret.length; ++i)
			ret[i] = iterator.next().intValue();
		return ret;
	}

	private HashSet<Integer> convertHashSet(final int[] list) {
		final HashSet<Integer> ret = new HashSet<Integer>();
		for (int i = 0; i<list.length; ++i)
			ret.add(Integer.valueOf(list[i]));
		return ret;
	}
}