package org.inventivetalent.nbt.test;

import org.inventivetalent.nbt.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TypeTest {

	@Test
	public void idForClassTest() {
		assertEquals(TagID.TAG_STRING, TagID.forClass(StringTag.class));
		assertEquals(TagID.TAG_COMPOUND, TagID.forClass(CompoundTag.class));
		assertEquals(TagID.TAG_INT_ARRAY, TagID.forClass(IntArrayTag.class));
	}

	@Test
	public void idForNameTest() {
		assertEquals(TagID.TAG_STRING, TagID.forName("StringTag"));
		assertEquals(TagID.TAG_STRING, TagID.forName("TAG_String"));
		assertEquals(TagID.TAG_COMPOUND, TagID.forName("TAG_Compound"));
		assertEquals(TagID.TAG_LONG, TagID.forName("LongTag"));
	}

	@Test
	public void idForValueClassTest() {
		assertEquals(TagID.TAG_BYTE, TagID.forValueClass(byte.class));
		assertEquals(TagID.TAG_BYTE, TagID.forValueClass(Byte.class));

		assertEquals(TagID.TAG_LONG, TagID.forValueClass(long.class));
		assertEquals(TagID.TAG_LONG, TagID.forValueClass(Long.class));

		assertEquals(TagID.TAG_BYTE_ARRAY, TagID.forValueClass(byte[].class));
		assertEquals(TagID.TAG_BYTE_ARRAY, TagID.forValueClass(Byte[].class));

		assertEquals(TagID.TAG_INT_ARRAY, TagID.forValueClass(int[].class));
		assertEquals(TagID.TAG_INT_ARRAY, TagID.forValueClass(Integer[].class));
	}

	@Test
	public void classForIdTest() {
		assertEquals(DoubleTag.class, NBTTag.forType(TagID.TAG_DOUBLE));
		assertEquals(CompoundTag.class, NBTTag.forType(TagID.TAG_COMPOUND));
		assertEquals(LongTag.class, NBTTag.forType(TagID.TAG_LONG));
	}

}
