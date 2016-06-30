package org.inventivetalent.nbt;

import com.google.gson.JsonPrimitive;
import lombok.EqualsAndHashCode;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
public class ByteTag extends NumberTag<Byte> {

	private  byte value;

	public ByteTag() {
		this((byte) 0);
	}

	public ByteTag(byte value) {
		super("");
		this.value = value;
	}

	public ByteTag(String name) {
		super(name);
	}

	public ByteTag(String name, byte value) {
		super(name);
		this.value = value;
	}

	@Override
	public Byte getValue() {
		return value;
	}

	@Override
	public void setValue(Byte aByte) {
		this.value = aByte;
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
