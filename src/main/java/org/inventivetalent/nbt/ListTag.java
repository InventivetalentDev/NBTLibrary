package org.inventivetalent.nbt;

import com.google.gson.JsonArray;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListTag extends NBTTag<List<NBTTag>> implements Iterable<NBTTag> {

	private final int          tagType;
	private final List<NBTTag> value;

	public ListTag(int tagType, List<NBTTag> value) {
		super("");
		this.tagType=tagType;
		this.value=value;
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

	public NBTTag get(int index) {
		return value.get(index);
	}

	// TODO: validate tag type
	public void add(NBTTag tag) {
		value.add(tag);
	}

	public void add(int index, NBTTag tag) {
		value.add(index, tag);
	}

	public void set(int index, NBTTag tag) {
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
}
