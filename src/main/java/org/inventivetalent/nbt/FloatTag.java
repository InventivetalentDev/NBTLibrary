package org.inventivetalent.nbt;

import com.google.gson.JsonPrimitive;
import lombok.EqualsAndHashCode;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
public class FloatTag extends NumberTag<Float> {

	private float value;

	public FloatTag() {
		this(0);
	}

	public FloatTag(float value) {
		super("");
		this.value = value;
	}

	public FloatTag(String name) {
		super(name);
	}

	public FloatTag(String name, float value) {
		super(name);
		this.value = value;
	}

	@Override
	public Float getValue() {
		return value;
	}

	@Override
	public void setValue(Float aFloat) {
		this.value = aFloat;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeFloat(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_FLOAT;
	}

	@Override
	public String getTypeName() {
		return "TAG_Float";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagFloat";
	}
}
