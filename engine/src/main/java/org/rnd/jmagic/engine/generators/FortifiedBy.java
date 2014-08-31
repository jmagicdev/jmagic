package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to everything any of the specified objects, if they're
 * fortifications, are fortified by.
 * 
 * As of Theros, this gets anything this object is attached to, even if it was
 * attached via enchanting or equipping. (This makes it exactly equivalent to
 * EnchantedBy, so that's what you get!)
 */
public class FortifiedBy
{
	public static SetGenerator instance(SetGenerator what)
	{
		return EnchantedBy.instance(what);
	}
}
