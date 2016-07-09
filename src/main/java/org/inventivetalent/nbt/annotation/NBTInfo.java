package org.inventivetalent.nbt.annotation;

import lombok.Data;

@Data
public class NBTInfo {

	protected final String[]    key;
	protected final int         type;
	protected final boolean     read;
	protected final boolean     write;
	protected final NBTPriority priority;

}
