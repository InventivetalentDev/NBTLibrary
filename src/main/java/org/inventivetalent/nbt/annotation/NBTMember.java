package org.inventivetalent.nbt.annotation;

import org.inventivetalent.nbt.NBTTag;

public abstract class NBTMember extends NBTInfo {

	protected final Object obj;

	public NBTMember(String[] key, int type, boolean read, boolean write, Object obj) {
		super(key, type, read, write);
		this.obj = obj;
	}

	public abstract void read(NBTTag tag);

	public abstract NBTTag write(NBTTag tag);

	protected Object fromNbtValue(NBTTag tag, Class<?> targetType) {
		if (NBTTag.class.isAssignableFrom(targetType)) {
			return tag;
		}
		Object nbt = tag.getValue();
		if (boolean.class.isAssignableFrom(targetType)) {
			if (nbt instanceof Boolean) {
				return (boolean) nbt;
			}
			if (nbt instanceof Byte) {
				return ((byte) nbt) == 1;
			}
			return false;
		} else {
			return nbt;
		}
	}

	protected Object toNbtValue(Object original, Class<?> sourceType) {
		if (boolean.class.isAssignableFrom(sourceType)) {
			return (byte) ((boolean) original ? 1 : 0);
		} else {
			return original;
		}
	}

}
