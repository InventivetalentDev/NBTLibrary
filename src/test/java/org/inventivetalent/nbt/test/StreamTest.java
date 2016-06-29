package org.inventivetalent.nbt.test;

import org.inventivetalent.nbt.*;
import org.inventivetalent.nbt.stream.NBTInputStream;
import org.inventivetalent.nbt.stream.NBTOutputStream;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamTest {

	@Test
	public void uncompressedInputTest() throws Exception {
		try (NBTInputStream in = new NBTInputStream(StreamTest.class.getResourceAsStream("/hello_world.nbt"))) {
			NBTTag nbtTag = in.readNBTTag();
			System.out.println(nbtTag);

			assertEquals(TagID.TAG_COMPOUND, nbtTag.getTypeId());
			assertEquals("hello world", nbtTag.getName());

			CompoundTag compoundTag = (CompoundTag) nbtTag;
			assertEquals(1, compoundTag.getValue().size());
			assertTrue(compoundTag.getValue().containsKey("name"));
			assertEquals(TagID.TAG_STRING, compoundTag.getValue().get("name").getTypeId());
			assertEquals("Bananrama", compoundTag.getValue().get("name").getValue());
		}
	}

	@Test
	public void compressedInputTest() throws Exception {
		try (NBTInputStream in = new NBTInputStream(StreamTest.class.getResourceAsStream("/bigtest.nbt"), true)) {
			NBTTag nbtTag = in.readNBTTag();
			System.out.println(nbtTag);

			assertEquals(TagID.TAG_COMPOUND, nbtTag.getTypeId());
			assertEquals("Level", nbtTag.getName());
			//TODO: extend the test
		}
	}

	@Test
	public void uncompressedOutputTest() throws Exception {
		try (NBTOutputStream out = new NBTOutputStream(new FileOutputStream("uncompressed_test.nbt"))) {
			CompoundTag compoundTag = new CompoundTag("uncompressed", new HashMap<String, NBTTag>() {
				{
					put("string", new StringTag("string", "Hello World!"));
				}
			});
			out.writeTag(compoundTag);
		}
	}

	@Test
	public void compressedOutputTest() throws Exception {
		try (NBTOutputStream out = new NBTOutputStream(new FileOutputStream("compressed_test.nbt"))) {
			CompoundTag compoundTag = new CompoundTag("uncompressed", new HashMap<String, NBTTag>() {
				{
					put("string", new StringTag("string", "Hello World!"));
					put("anotherCompound", new CompoundTag("anotherCompound", new HashMap<String, NBTTag>() {
						{
							put("int", new IntTag("int", Integer.MAX_VALUE));
							put("double", new DoubleTag("double", Double.MIN_VALUE));
						}
					}));
				}
			});
			out.writeTag(compoundTag);
		}
	}

}
