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
public class ShortTag extends NBTTag<Short> {

	private final short value;

	public ShortTag() {
		this((short) 0);
	}

	public ShortTag(short value) {
		super("");this.value=value;
	}

	public ShortTag(String name, short value) {
		super(name);
		this.value = value;
	}

	@Override
	public Short getValue() {
		return value;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeShort(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_SHORT;
	}

	@Override
	public String getTypeName() {
		return "TAG_Short";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagShort";
	}
}
