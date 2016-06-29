package org.inventivetalent.nbt.stream;

import org.inventivetalent.nbt.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import static org.inventivetalent.nbt.TagID.*;

public class NBTInputStream implements AutoCloseable {

	public static final Charset UTF_8 = Charset.forName("UTF-8");

	private final DataInputStream in;

	public NBTInputStream(InputStream inputStream, boolean gzip) throws IOException {
		if (gzip) {
			this.in = new DataInputStream(new GZIPInputStream(inputStream));
		} else {
			this.in = new DataInputStream(inputStream);
		}
	}

	public NBTInputStream(InputStream inputStream) throws IOException {
		this.in = new DataInputStream(inputStream);
	}

	public NBTInputStream(DataInputStream dataInputStream) {
		this.in = dataInputStream;
	}

	public NBTTag readNBTTag() throws IOException {
		return readNBTTag(0);
	}

	public NBTTag readNBTTag(int depth) throws IOException {
		int tagType = in.readByte() & 0xFF;
		String tagName = "";
		if (tagType != TAG_END) {
			int length = in.readShort() & 0xFFFF;
			byte[] nameBytes = new byte[length];
			in.readFully(nameBytes);
			tagName = new String(nameBytes, UTF_8);
		}

		return readTagContent(tagType, tagName, depth);
	}

	public NBTTag readTagContent(int tagType, String tagName, int depth) throws IOException {
		switch (tagType) {
			case TAG_END:
				return new EndTag();
			case TAG_BYTE:
				return new ByteTag(tagName, in.readByte());
			case TAG_DOUBLE:
				return new DoubleTag(tagName, in.readDouble());
			case TAG_FLOAT:
				return new FloatTag(tagName, in.readFloat());
			case TAG_INT:
				return new IntTag(tagName, in.readInt());
			case TAG_LONG:
				return new LongTag(tagName, in.readLong());
			case TAG_SHORT:
				return new ShortTag(tagName, in.readShort());
			case TAG_STRING: {
				int length = in.readShort();
				byte[] bytes = new byte[length];
				in.readFully(bytes);
				return new StringTag(tagName, new String(bytes, UTF_8));
			}
			case TAG_BYTE_ARRAY: {
				int length = in.readInt();
				byte[] bytes = new byte[length];
				in.readFully(bytes);
				return new ByteArrayTag(tagName, bytes);
			}
			case TAG_INT_ARRAY: {
				int length = in.readInt();
				int[] ints = new int[length];
				for (int i = 0; i < length; i++) {
					ints[i] = in.readInt();
				}
				return new IntArrayTag(tagName, ints);
			}
			case TAG_COMPOUND: {
				Map<String, NBTTag> tags = new HashMap<>();
				while (true) {
					NBTTag tag = readNBTTag(depth + 1);
					if (tag.getTypeId() == TAG_END) {
						break;
					} else {
						tags.put(tag.getName(), tag);
					}
				}
				return new CompoundTag(tagName, tags);
			}
			case TAG_LIST: {
				int listType = in.readByte();
				int length = in.readInt();

				List<NBTTag> tags = new ArrayList<>();
				for (int i = 0; i < length; i++) {
					NBTTag tag = readTagContent(listType, "", depth + 1);
					if (tag.getTypeId() == TAG_END) {
						throw new IOException("Invalid TAG_End in TAG_List (not allowed)");
					}
					tags.add(tag);
				}
				return new ListTag(tagName, listType, tags);
			}
			default:
				throw new IOException("Invalid NBTTag type: " + tagType + " (with name '" + tagName + "')");
		}
	}

	@Override
	public void close() throws Exception {
		in.close();
	}
}
