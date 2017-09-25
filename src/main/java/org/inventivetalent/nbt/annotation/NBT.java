package org.inventivetalent.nbt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
		ElementType.FIELD,
		ElementType.METHOD,
		ElementType.PARAMETER})
public @interface NBT {

	/**
	 * Path for this NBT value. Every entry of the array is a TAG_Compound, but the last entry matches {@link #type()}/the field type
	 *
	 * @return NBT path
	 */
	String[] value() default {};

	/**
	 * Type of this NBT value
	 *
	 * @return NBT type
	 * @see org.inventivetalent.nbt.TagID
	 */
	int type() default -1;

	/**
	 * Whether to write this value to NBT
	 *
	 * @return whether to write
	 */
	boolean write() default true;

	/**
	 * Whether to read this value from NBT
	 *
	 * @return whether to read
	 */
	boolean read() default true;

	NBTPriority priority() default NBTPriority.NORMAL;

}
