package org.inventivetalent.nbt;

import com.google.gson.JsonPrimitive;
import lombok.EqualsAndHashCode;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
public class LongTag extends NumberTag<Long> {

	private long value;

	public LongTag() {
		this(0);
	}

	public LongTag(long value) {
		super("");
		this.value = value;
	}

	public LongTag(String name, long value) {
		super(name);
		this.value = value;
	}

	@Override
	public Long getValue() {
		return value;
	}

	@Override
	public void setValue(Long aLong) {
		this.value = aLong;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeLong(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_LONG;
	}

	@Override
	public String getTypeName() {
		return "TAG_Long";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagLong";
	}
}
