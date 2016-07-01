package org.inventivetalent.nbt;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import org.inventivetalent.nbt.stream.NBTInputStream;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EndTag extends NBTTag<Void> {

	public EndTag() {
		this("");
	}

	public EndTag(String name) {
		super(name);
	}

	@Override
	public Void getValue() {
		return null;
	}

	@Override
	public void setValue(Void aVoid) {
	}

	@Override
	public JsonElement asJson() {
		return JsonNull.INSTANCE;
	}

	@Override
	public void read(NBTInputStream nbtIn, DataInputStream in, int depth) throws IOException {
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_END;
	}

	@Override
	public String getTypeName() {
		return "TAG_End";
	}

	@Override
	public String getNMSClass() {
		return "NBTTagEnd";
	}

	public NBTTag fromNMS(Object nms) throws ReflectiveOperationException {
		return new EndTag();
	}

	public Object toNMS() throws ReflectiveOperationException {
		Class<?> clazz = NMS_CLASS_RESOLVER.resolve(getNMSClass());
		return clazz.newInstance();
	}
}
