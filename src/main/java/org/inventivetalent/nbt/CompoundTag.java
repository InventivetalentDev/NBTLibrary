package org.inventivetalent.nbt;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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

	public CompoundTag(String name, Map<String, NBTTag> value) {
		super(name);
		this.value = new HashMap<>(value);
	}

	@Override
	public Map<String, NBTTag> getValue() {
		return value;
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
}
