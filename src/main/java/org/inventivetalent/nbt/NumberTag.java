package org.inventivetalent.nbt;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class NumberTag<V extends Number> extends NBTTag<V> {
	public NumberTag(String name) {
		super(name);
	}

	@Override
	public V getValue() {
		return null;
	}

	@Override
	public Number getAsNumber() {
		return getValue();
	}
}
