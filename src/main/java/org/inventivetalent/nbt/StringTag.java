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
public class StringTag extends NBTTag<String> {

	private final String value;

	public StringTag(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public JsonPrimitive asJson() {
		return new JsonPrimitive(value);
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
}
