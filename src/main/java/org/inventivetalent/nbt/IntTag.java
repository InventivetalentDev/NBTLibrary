package org.inventivetalent.nbt;

import com.google.gson.JsonPrimitive;
import lombok.EqualsAndHashCode;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
public class IntTag extends NumberTag<Integer> {

	private int value;

	public IntTag() {
		this(0);
	}

	public IntTag(int value) {
		super("");
		this.value = value;
	}

	public IntTag(String name) {
		super(name);
	}

	public IntTag(String name, int value) {
		super(name);
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void setValue(Integer integer) {
		this.value = integer;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeInt(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_INT;
	}

	@Override
	public String getTypeName() {
		return "TAG_Int";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagInt";
	}
}
