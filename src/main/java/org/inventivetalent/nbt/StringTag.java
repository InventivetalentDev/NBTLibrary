package org.inventivetalent.nbt;

import com.google.gson.JsonPrimitive;
import org.inventivetalent.nbt.stream.NBTInputStream;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringTag extends NBTTag<String> {

	private String value;

	public StringTag() {
		this("");
	}

	public StringTag(String value) {
		super("");
		this.value = value;
	}

	public StringTag(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
		byte[] bytes = new byte[in.readShort()];
		in.readFully(bytes);
		value = new String(bytes, NBTInputStream.UTF_8);
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		byte[] bytes = value.getBytes(NBTOutputStream.UTF_8);
		out.writeShort(bytes.length);
		out.write(bytes);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_STRING;
	}

	@Override
	public String getTypeName() {
		return "TAG_String";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagString";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		StringTag stringTag = (StringTag) o;

		return value != null ? value.equals(stringTag.value) : stringTag.value == null;

	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
