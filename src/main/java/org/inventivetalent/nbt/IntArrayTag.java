package org.inventivetalent.nbt;

import com.google.common.primitives.Ints;
import com.google.gson.JsonArray;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.inventivetalent.nbt.stream.NBTOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IntArrayTag extends ArrayTag<int[], Integer> {

	private final int[] value;

	public IntArrayTag() {
		this(new int[0]);
	}

	public IntArrayTag(int[] value) {
		super("");
		this.value = value;
	}

	public IntArrayTag(String name, int[] value) {
		super(name);
		this.value = value;
	}

	@Override
	public int[] getValue() {
		return value;
	}

	@Override
	public JsonArray asJson() {
		JsonArray jsonArray = new JsonArray();
		for (int i : value) {
			jsonArray.add(i);
		}
		return jsonArray;
	}

	@Override
	public void write(NBTOutputStream nbtOut, DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for (int i = 0; i < value.length; i++) {
			out.writeInt(value[i]);
		}
	}

	@Override
	public int getTypeId() {
		return TagID.TAG_INT_ARRAY;
	}

	@Override
	public String getTypeName() {
		return "TAG_Int_Array";
	}

	@Override
	public Iterator<Integer> iterator() {
		return Ints.asList(value).iterator();
	}

	@Override
	public String getNMSClass() {
		return "NBTTagIntArray";
	}
}
