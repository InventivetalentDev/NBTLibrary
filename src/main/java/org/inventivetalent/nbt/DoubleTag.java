package org.inventivetalent.nbt;

import com.google.gson.JsonPrimitive;
import lombok.EqualsAndHashCode;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
public class DoubleTag extends NumberTag<Double> {

	private double value;

	public DoubleTag() {
		this(0);
	}

	public DoubleTag(double value) {
		super("");
		this.value = value;
	}

	public DoubleTag(String name, double value) {
		super(name);
		this.value = value;
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public void setValue(Double aDouble) {
		this.value = aDouble;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeDouble(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_DOUBLE;
	}

	@Override
	public String getTypeName() {
		return "TAG_Double";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagDouble";
	}
}
