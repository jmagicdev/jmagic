package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to everything any of the specified objects, if they're equipments,
 * are equipped by.
 * 
 * As of Theros, this gets anything this object is attached to, even if it was
 * attached via enchanting or fortifying. (This makes it exactly equivalent to
 * EnchantedBy, so that's what you get!)
 */
public class EquippedBy
{
	public static SetGenerator instance(SetGenerator what)
	{
		return EnchantedBy.instance(what);
	}
}
