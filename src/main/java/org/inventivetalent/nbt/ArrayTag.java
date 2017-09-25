package org.inventivetalent.nbt;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ArrayTag<V, A> extends NBTTag<V> implements Iterable<A> {
	public ArrayTag(String name) {
		super(name);
	}
}
