package org.inventivetalent.nbt.annotation;

import lombok.Data;

@Data
public class NBTInfo implements Comparable<NBTInfo> {

	protected final String[] key;
	protected final int type;
	protected final boolean read;
	protected final boolean write;
	protected final NBTPriority priority;

	@Override
	public int compareTo(NBTInfo o) {
		return priority.ordinal() - o.priority.ordinal();
	}
}
