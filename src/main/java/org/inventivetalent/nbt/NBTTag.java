package org.inventivetalent.nbt;

import com.google.gson.JsonElement;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;

import static org.inventivetalent.nbt.TagID.*;

@Data
@RequiredArgsConstructor
public abstract class NBTTag<V> {

	private final String name;

	public abstract V getValue();

	public abstract JsonElement asJson();

	public abstract int getTypeId();

	public abstract String getTypeName();

	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
	}

	public static Class<? extends NBTTag> forType(int type) {
		switch (type) {
			case TAG_BYTE_ARRAY:
				return ByteArrayTag.class;
			case TAG_BYTE:
				return ByteTag.class;
			case TAG_COMPOUND:
				return CompoundTag.class;
			case TAG_DOUBLE:
				return DoubleTag.class;
			case TAG_END:
				return EndTag.class;
			case TAG_FLOAT:
				return FloatTag.class;
			case TAG_INT_ARRAY:
				return IntArrayTag.class;
			case TAG_INT:
				return IntTag.class;
			case TAG_LIST:
				return ListTag.class;
			case TAG_LONG:
				return LongTag.class;
			case TAG_SHORT:
				return ShortTag.class;
			case TAG_STRING:
				return StringTag.class;
			default:
				throw new IllegalArgumentException("Invalid NBTTag type " + type);
		}
	}

}
