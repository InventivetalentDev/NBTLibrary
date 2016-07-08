package org.inventivetalent.nbt.test;

import org.inventivetalent.nbt.wrapper.BooleanTag;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WrapperTest {

	@Test
	public void booleanWrapperTest() {
		BooleanTag tag1 = new BooleanTag(true);
		assertEquals(true, tag1.getBoolean());
		assertEquals((long) 1, (long) tag1.getValue());

		BooleanTag tag2 = new BooleanTag(false);
		assertEquals(false, tag2.getBoolean());
		assertEquals((long) 0, (long) tag2.getValue());
	}

}
