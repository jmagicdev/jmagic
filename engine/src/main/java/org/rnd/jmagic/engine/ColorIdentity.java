package org.rnd.jmagic.engine;

/**
 * This class only represents the color identity of this particular class. To
 * get the complete color identity of a card, you'll also need to get this
 * annotation from any alternate face annotations.
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ColorIdentity
{
	Color[] value();
}
