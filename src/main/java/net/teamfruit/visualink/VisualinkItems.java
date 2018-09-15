package net.teamfruit.visualink;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.teamfruit.visualink.addons.IItemIdentifierProvider;
import net.teamfruit.visualink.addons.enderstorage.EnderStorageModule;

public class VisualinkItems {
	public static List<VisualinkItems> items = new ArrayList<VisualinkItems>();
	public final String id;
	public final @Nullable IItemIdentifierProvider provider;
	private final AtomicReference<Object> item = new AtomicReference<Object>();

	public VisualinkItems(final String id, @Nullable final IItemIdentifierProvider provider) {
		this.id = id;
		this.provider = provider;
	}

	public Item getItem() {
		Object value = this.item.get();
		if (value==null)
			synchronized (this.item) {
				value = this.item.get();
				if (value==null) {
					final Object actualValue = Item.itemRegistry.getObject(this.id);
					value = actualValue==null ? this.item : actualValue;
					this.item.set(value);
				}
			}
		return (Item) (value==this.item ? null : value);
	}

	@Override
	public String toString() {
		return String.format("VisualinkBlocks [id=%s, provider=%s]", this.id, this.provider);
	}

	public static void setStandardList() {
		EnderStorageModule.registerItems(items);
	}

	public static void init() {
		setStandardList();
	}
}