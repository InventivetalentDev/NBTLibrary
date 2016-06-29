package org.inventivetalent.nbt;

import com.google.gson.JsonPrimitive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DoubleTag extends NBTTag<Double> {

	private final double value;

	public DoubleTag(String name, double value) {
		super(name);
		this.value = value;
	}

	@Override
	public Double getValue() {
		return value;
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
}
