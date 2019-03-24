package org.inventivetalent.nbt;

import com.google.common.primitives.Longs;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang.ArrayUtils;
import org.inventivetalent.nbt.stream.NBTInputStream;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LongArrayTag extends ArrayTag<long[], Long> {

	private long[] value;

	public LongArrayTag() {
		this(new long[0]);
	}

	public LongArrayTag(long[] value) {
		super("");
		this.value = value;
	}

	public LongArrayTag(String name) {
		super(name);
	}

	public LongArrayTag(String name, long[] value) {
		super(name);
		this.value = value;
	}

	public LongArrayTag(String name, List<Long> list) {
		super(name);
		this.value = ArrayUtils.toPrimitive(list.toArray(new Long[list.size()]));
	}

	@Override
	public long[] getValue() {
		return value;
	}

	@Override
	public void setValue(long[] value) {
		this.value = value;
	}

	@Override
	public JsonArray asJson() {
		JsonArray jsonArray = new JsonArray();
		for (long l : value) {
			jsonArray.add(new JsonPrimitive(l));
		}
		return jsonArray;
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		long[] longs = new long[in.readInt()];
		for (int i = 0; i < longs.length; i++) {
			longs[i] = in.readLong();
		}
		value = longs;
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for (int i = 0; i < value.length; i++) {
			out.writeLong(value[i]);
		}
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_LONG_ARRAY;
	}

	@Override
	public String getTypeName() {
		return "TAG_Long_Array";
	}

	@Override
	public Iterator<Long> iterator() {
		return Longs.asList(value).iterator();
	}

	@Override
	public String getNMSClass() {
		return "NBTTagLongArray";
	}

	@Override
	public String toString() {
		return getTypeName() + "(" + getName() + "): " + Arrays.toString(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		LongArrayTag longs = (LongArrayTag) o;

		return Arrays.equals(value, longs.value);

	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(value);
	}
}
