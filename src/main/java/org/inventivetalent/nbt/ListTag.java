package org.inventivetalent.nbt;

import com.google.gson.JsonArray;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListTag extends NBTTag<List<NBTTag>> implements Iterable<NBTTag> {

	private final int          tagType;
	private final List<NBTTag> value;

	public ListTag(int tagType) {
		this(tagType, new ArrayList<NBTTag>());
	}

	public ListTag(int tagType, List<NBTTag> value) {
		super("");
		this.tagType = tagType;
		this.value = value;
	}

	public ListTag(int tagType, String name) {
		super(name);
		this.tagType = tagType;
		this.value = new ArrayList<>();
	}

	public ListTag(String name, int tagType, List<NBTTag> value) {
		super(name);
		this.tagType = tagType;
		this.value = new ArrayList<>(value);
	}

	public int getTagType() {
		return tagType;
	}

	@Override
	public List<NBTTag> getValue() {
		return value;
	}

	@Override
	public void setValue(List<NBTTag> value) {
		this.value.addAll(value);
	}

	public NBTTag get(int index) {
		return value.get(index);
	}

	public void add(NBTTag tag) {
		if (tag.getTypeId() != getTagType()) { throw new IllegalArgumentException("Invalid Tag type (List: " + getTagType() + ", Tag: " + tag.getTypeId() + ")"); }
		value.add(tag);
	}

	public void add(int index, NBTTag tag) {
		if (tag.getTypeId() != getTagType()) { throw new IllegalArgumentException("Invalid Tag type (List: " + getTagType() + ", Tag: " + tag.getTypeId() + ")"); }
		value.add(index, tag);
	}

	public void set(int index, NBTTag tag) {
		if (tag.getTypeId() != getTagType()) { throw new IllegalArgumentException("Invalid Tag type (List: " + getTagType() + ", Tag: " + tag.getTypeId() + ")"); }
		value.set(index, tag);
	}

	@Override
	public JsonArray asJson() {
		JsonArray jsonArray = new JsonArray();
		for (NBTTag tag : value) {
			jsonArray.add(tag.asJson());
		}
		return jsonArray;
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeByte(getTagType());
		out.writeInt(value.size());
		for (NBTTag tag : value) {
			nbtOut.writeTagContent(tag);
		}
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_LIST;
	}

	@Override
	public String getTypeName() {
		return "TAG_List";
	}

	@Override
	public Iterator<NBTTag> iterator() {
		return value.iterator();
	}

	@Override
	public String getNMSClass() {
		return "NBTTagList";
	}

	@Override
	public ListTag fromNMS(Object nms) throws ReflectiveOperationException {
		Class<?> clazz = NMS_CLASS_RESOLVER.resolve(getNMSClass());

		Field typeField = clazz.getDeclaredField("type");
		typeField.setAccessible(true);
		byte typeId = typeField.getByte(nms);

		Field field = clazz.getDeclaredField("list");
		field.setAccessible(true);
		List<Object> nmsList = (List<Object>) field.get(nms);

		for (Object o : nmsList) {
			NBTTag nbtTag = NBTTag.forType(typeId).newInstance();
			if (nbtTag.getTypeId() == TagID.TAG_END) { continue; }
			add(nbtTag.fromNMS(o));
		}
		return this;
	}

	@Override
	public Object toNMS() throws ReflectiveOperationException {
		Class<?> clazz = NMS_CLASS_RESOLVER.resolve(getNMSClass());
		Field field = clazz.getDeclaredField("list");
		field.setAccessible(true);
		Object nms = clazz.newInstance();
		List list = (List) field.get(nms);
		for (NBTTag tag : this) {
			list.add(tag.toNMS());
		}
		field.set(nms, list);
		return nms;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		ListTag listTag = (ListTag) o;

		if (tagType != listTag.tagType) { return false; }
		return value != null ? value.equals(listTag.value) : listTag.value == null;

	}

	@Override
	public int hashCode() {
		int result = tagType;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
