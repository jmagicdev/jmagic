package org.rnd.jmagic.engine;

@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Flipped
{
	Class<? extends Card> value();
}
