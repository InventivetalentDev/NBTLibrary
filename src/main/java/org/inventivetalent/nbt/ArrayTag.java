package org.inventivetalent.nbt;

public abstract class ArrayTag<V, A> extends NBTTag<V> implements Iterable<A> {
	public ArrayTag(String name) {
		super(name);
	}
}
