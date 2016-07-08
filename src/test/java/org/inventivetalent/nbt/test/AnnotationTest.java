package org.inventivetalent.nbt.test;

import org.inventivetalent.nbt.CompoundTag;
import org.inventivetalent.nbt.ListTag;
import org.inventivetalent.nbt.annotation.AnnotatedNBTHandler;
import org.inventivetalent.nbt.annotation.NBT;
import org.inventivetalent.nbt.stream.NBTInputStream;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnnotationTest {

	private final AnnotatedNBTHandler nbtHandler = new AnnotatedNBTHandler(this);

	@NBT("name")
	public String helloWorldName;
	boolean helloWorldNameCalled;

	@NBT({
				 "nested compound test",
				 "egg",
				 "name" })
	public String nestedEggName;
	boolean nestedEggCalled;
	boolean nestedHamCalled;
	boolean listCalled;

	@NBT("name")
	public void helloWorldName(@NBT String name) {
		helloWorldNameCalled = true;
		assertEquals("Bananrama", name);
	}

	@NBT({
				 "nested compound test",
				 "egg" })
	public void nestedEgg(@NBT("name") String name, @NBT("value") float value) {
		nestedEggCalled = true;
		assertEquals("Eggbert", name);
		assertEquals(0.5, value, 0);
	}

	@NBT({
				 "nested compound test",
				 "ham" })
	public void nestedHam(@NBT("name") String name, @NBT("value") float value) {
		nestedHamCalled = true;
		assertEquals("Hampus", name);
		assertEquals(0.75, value, 0);
	}

	@NBT("listTest (long)")
	public void list(@NBT ListTag listTag) {
		listCalled = true;
		assertEquals(5, listTag.size());
	}

	@Test
	public void uncompressedReadTest() throws Exception {
		try (NBTInputStream in = new NBTInputStream(StreamTest.class.getResourceAsStream("/hello_world.nbt"))) {
			CompoundTag tag = (CompoundTag) in.readNBTTag();
			nbtHandler.onRead(tag);
		}

		assertEquals("Bananrama", helloWorldName);
		assertTrue(helloWorldNameCalled);
	}

	@Test
	public void compressedReadTest() throws Exception {
		try (NBTInputStream in = new NBTInputStream(StreamTest.class.getResourceAsStream("/bigtest.nbt"), true)) {
			CompoundTag tag = (CompoundTag) in.readNBTTag();
			nbtHandler.onRead(tag);

			assertEquals("Eggbert", nestedEggName);
			assertTrue(nestedEggCalled);
			assertTrue(nestedHamCalled);
			assertTrue(listCalled);
		}
	}

}
