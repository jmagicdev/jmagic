package org.rnd.jmagic.engine;

@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Printings
{
	@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
	@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
	public static @interface Printed
	{
		Class<? extends Expansion> ex();

		Rarity r();
	}

	Printed[] value();
}
