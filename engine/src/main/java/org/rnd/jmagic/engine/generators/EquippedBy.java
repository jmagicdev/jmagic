package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to everything any of the specified objects, if they're equipments,
 * are equipped by.
 */
public class EquippedBy extends SetGenerator
{
	public static EquippedBy instance(SetGenerator what)
	{
		return new EquippedBy(what);
	}

	private final SetGenerator what;

	private EquippedBy(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Set<Integer> attachments = new java.util.HashSet<Integer>();
		for(GameObject o: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			if(o.getSubTypes().contains(SubType.EQUIPMENT) && (-1 != o.getAttachedTo()))
				attachments.add(o.getAttachedTo());
		return IdentifiedWithID.instance(attachments).evaluate(state, thisObject);
	}
}
