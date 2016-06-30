package org.inventivetalent.nbt;

import com.google.gson.JsonObject;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompoundTag extends NBTTag<Map<String, NBTTag>> implements Iterable<Map.Entry<String, NBTTag>> {

	private final Map<String, NBTTag> value;

	public CompoundTag() {
		super("");
		this.value = new HashMap<>();
	}

	public CompoundTag(Map<String, NBTTag> value) {
		super("");
		this.value = new HashMap<>(value);
	}

	public CompoundTag(String name) {
		super(name);
		this.value = new HashMap<>();
	}

	public CompoundTag(String name, Map<String, NBTTag> value) {
		super(name);
		this.value = new HashMap<>(value);
	}

	@Override
	public Map<String, NBTTag> getValue() {
		return value;
	}

	@Override
	public void setValue(Map<String, NBTTag> value) {
		this.value.putAll(value);
	}

	public NBTTag get(String name) {
		return value.get(name);
	}

	public void set(String name, NBTTag tag) {
		this.value.put(name, tag);
	}

	public void set(String name, byte b) {
		set(name, new ByteTag(name, b));
	}

	public void set(String name, short s) {
		set(name, new ShortTag(name, s));
	}

	public void set(String name, int i) {
		set(name, new IntTag(name, i));
	}

	public void set(String name, long l) {
		set(name, new LongTag(name, l));
	}

	public void set(String name, float f) {
		set(name, new FloatTag(name, f));
	}

	public void set(String name, double d) {
		set(name, new DoubleTag(name, d));
	}

	public void set(String name, String string) {
		set(name, new StringTag(name, string));
	}

	public void set(String name, byte[] b) {
		set(name, new ByteArrayTag(name, b));
	}

	public void set(String name, int[] i) {
		set(name, new IntArrayTag(name, i));
	}

	public void set(String name, boolean b) {
		set(name, (byte) (b ? 1 : 0));
	}

	@Override
	public JsonObject asJson() {
		JsonObject jsonObject = new JsonObject();
		for (Map.Entry<String, NBTTag> entry : value.entrySet()) {
			jsonObject.add(entry.getKey(), entry.getValue().asJson());
		}
		return jsonObject;
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		for (NBTTag tag : value.values()) {
			nbtOut.writeTag(tag);
		}
		out.writeByte(TagID.TAG_END);
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_COMPOUND;
	}

	@Override
	public String getTypeName() {
		return "TAG_Compund";
	}

	@Override
	public Iterator<Map.Entry<String, NBTTag>> iterator() {
		return value.entrySet().iterator();
	}

	public String getNMSClass() {
		return "NBTTagCompound";
	}

	@Override
	public CompoundTag fromNMS(Object nms) throws ReflectiveOperationException {
		Class<?> clazz = NMS_CLASS_RESOLVER.resolve(getNMSClass());
		Class<?> nbtBaseClass = NMS_CLASS_RESOLVER.resolve("NBTBase");
		Field field = clazz.getDeclaredField("map");
		field.setAccessible(true);
		Map<String, Object> nmsMap = (Map<String, Object>) field.get(nms);

		for (Map.Entry<String, Object> nmsEntry : nmsMap.entrySet()) {
			byte typeId = (byte) nbtBaseClass.getMethod("getTypeId").invoke(nmsEntry.getValue());
			if (typeId == TagID.TAG_END) { continue; }
			if (typeId == TagID.TAG_LIST) {
				set(nmsEntry.getKey(), new ListTag(typeId, nmsEntry.getKey()).fromNMS(nmsEntry.getValue()));
			} else if (typeId == TagID.TAG_STRING) {
				set(nmsEntry.getKey(), new StringTag(nmsEntry.getKey(), "null").fromNMS(nmsEntry.getValue()));
			} else {
				set(nmsEntry.getKey(), NBTTag.forType(typeId).newInstance().fromNMS(nmsEntry.getValue()));
			}
		}
		return this;
	}

	@Override
	public Object toNMS() throws ReflectiveOperationException {
		Class<?> clazz = NMS_CLASS_RESOLVER.resolve(getNMSClass());
		Field field = clazz.getDeclaredField("map");
		field.setAccessible(true);
		Object nms = clazz.newInstance();
		Map map = (Map) field.get(nms);
		for (Map.Entry<String, NBTTag> entry : this) {
			map.put(entry.getKey(), entry.getValue().toNMS());
		}
		field.set(nms, map);// I don't quite get why this doesn't complain about illegal access (the field is private final)
		return nms;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		CompoundTag that = (CompoundTag) o;

		return value != null ? value.equals(that.value) : that.value == null;

	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
