package org.inventivetalent.nbt.test;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.inventivetalent.nbt.CompoundTag;
import org.inventivetalent.nbt.NBTTag;
import org.inventivetalent.nbt.TagID;
import org.inventivetalent.nbt.stream.NBTInputStream;
import org.inventivetalent.reflection.resolver.minecraft.NMSClassResolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NMSTest {

	String compressedToString;

	NBTTagCompound uncompressedTag;
	NBTTagCompound compressedTag;

	public NMSTest() {
		NBTTag.NMS_CLASS_RESOLVER = new NMSClassResolver() {
			public Class resolve(String... names) throws ClassNotFoundException {
				for (int i = 0; i < names.length; ++i) {
					if (!names[i].startsWith("net.minecraft.server")) {
						names[i] = "net.minecraft.server.v1_10_R1." + names[i];
					}
				}

				return super.resolve(names);
			}
		};
	}

	@Test
	public void uncompressedToNMSTest() throws Exception {
		try (NBTInputStream in = new NBTInputStream(StreamTest.class.getResourceAsStream("/hello_world.nbt"))) {
			CompoundTag compoundTag = (CompoundTag) in.readNBTTag();
			System.out.println(compoundTag);
			uncompressedTag = (NBTTagCompound) compoundTag.toNMS();
			System.out.println(uncompressedTag);

			assertEquals(TagID.TAG_STRING, uncompressedTag.get("name").getTypeId());
			assertEquals("Bananrama",  uncompressedTag.getString("name"));
		}
	}

	@Test
	public void compressedToNMSTest( ) throws Exception {
		try (NBTInputStream in = new NBTInputStream(StreamTest.class.getResourceAsStream("/bigtest.nbt"),true)) {
			CompoundTag compoundTag = (CompoundTag) in.readNBTTag();
			System.out.println(compoundTag);
			compressedTag = (NBTTagCompound) compoundTag.toNMS();
			System.out.println(compressedTag);

			compressedToString = compoundTag.toString();
		}
	}

	@Test
	public void compressedFromNMSTest() throws Exception {
		compressedToNMSTest();

		CompoundTag compoundTag = new CompoundTag().fromNMS(compressedTag);
		System.out.println(compoundTag);

		assertEquals(compressedToString,compoundTag.toString());
	}

}
