package org.inventivetalent.nbt;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompoundTag extends NBTTag<Map<String, NBTTag>> {

	private final Map<String, NBTTag> value;

	public CompoundTag(String name, Map<String, NBTTag> value) {
		super(name);
		this.value = Collections.unmodifiableMap(value);
	}

	@Override
	public Map<String, NBTTag> getValue() {
		return value;
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
	public void write(NBTOutputStream nbtOut,DataOutputStream out) throws IOException {
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
}
