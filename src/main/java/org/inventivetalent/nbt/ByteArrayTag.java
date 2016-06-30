package org.inventivetalent.nbt;

import com.google.common.primitives.Bytes;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import lombok.EqualsAndHashCode;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

@EqualsAndHashCode(callSuper = true)
public class ByteArrayTag extends ArrayTag<byte[], Byte> {

	private byte[] value;

	public ByteArrayTag() {
		this(new byte[0]);
	}

	public ByteArrayTag(byte[] value) {
		super("");
		this.value = value;
	}

	public ByteArrayTag(String name) {
		super(name);
	}

	public ByteArrayTag(String name, byte[] value) {
		super(name);
		this.value = value;
	}

	@Override
	public byte[] getValue() {
		return value;
	}

	@Override
	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	public JsonArray asJson() {
		JsonArray jsonArray = new JsonArray();
		for (byte b : value) {
			jsonArray.add(new JsonPrimitive(b));
		}
		return jsonArray;
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		out.write(value);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_BYTE_ARRAY;
	}

	@Override
	public String getTypeName() {
		return "TAG_Byte_Array";
	}

	@Override
	public Iterator<Byte> iterator() {
		return Bytes.asList(value).iterator();
	}

	@Override
	public String getNMSClass() {
		return "NBTTagByteArray";
	}

}
