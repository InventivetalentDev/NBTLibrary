package org.inventivetalent.nbt;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EndTag extends NBTTag<Void> {

	public EndTag(String name) {
		super(name);
	}

	public EndTag() {
		this("");
	}

	@Override
	public Void getValue() {
		return null;
	}

	@Override
	public JsonElement asJson() {
		return JsonNull.INSTANCE;
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
		Class<?> clazz = NMS_CLASS_RESOLVER.resolve(getNMSClass());
		Field field = clazz.getDeclaredField("data");
		return (NBTTag) getClass().getConstructors()[0].newInstance("", field.get(nms));
	}

	public Object toNMS() throws ReflectiveOperationException {
		Class<?> clazz = NMS_CLASS_RESOLVER.resolve(getNMSClass());
		return clazz.getConstructors()[0].newInstance(getValue());
	}
}
