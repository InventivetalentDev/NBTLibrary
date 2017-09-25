package org.inventivetalent.nbt;

import org.inventivetalent.nbt.annotation.AnnotatedNBTHandler;
import org.inventivetalent.nbt.annotation.NBT;
import org.inventivetalent.nbt.annotation.NBTPriority;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "WeakerAccess"})
public class PriorityTest {

	private int c = 0;

	private int highest;
	private int high;
	private int normal;
	private int low;
	private int lowest;

	@NBT(priority = NBTPriority.HIGHEST)
	void highest(@NBT String s) {
		highest = c++;
	}

	@NBT(priority = NBTPriority.HIGH)
	void high(@NBT String s) {
		high = c++;
	}

	@NBT(priority = NBTPriority.NORMAL)
	void normal(@NBT String s) {
		normal = c++;
	}

	@NBT(priority = NBTPriority.LOW)
	void low(@NBT String s) {
		low = c++;
	}

	@NBT(priority = NBTPriority.LOWEST)
	void lowest(@NBT String s) {
		lowest = c++;
	}

	@Test
	public void priorityTest() {
		AnnotatedNBTHandler handler = new AnnotatedNBTHandler(this);
		handler.onRead(new CompoundTag() {
			{
				set("highest", "highest");
				set("high", "high");
				set("normal", "normal");
				set("low", "low");
				set("lowest", "lowest");
			}
		});

		assertEquals(0, highest);
		assertEquals(1, high);
		assertEquals(2, normal);
		assertEquals(3, low);
		assertEquals(4, lowest);
	}

}
