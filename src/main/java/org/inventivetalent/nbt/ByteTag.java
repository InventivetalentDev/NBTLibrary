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
public class ByteTag extends NBTTag<Byte> {

	private final byte value;

	public ByteTag(String name, byte value) {
		super(name);
		this.value = value;
	}

	@Override
	public Byte getValue() {
		return value;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeByte(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_BYTE;
	}

	@Override
	public String getTypeName() {
		return "TAG_Byte";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagByte";
	}
}
