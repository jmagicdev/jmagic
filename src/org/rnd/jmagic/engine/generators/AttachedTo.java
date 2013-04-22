package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to everything attached to any of the given objects. This is the
 * generator you want for "all enchantments attached to ~" or the like.
 */
public class AttachedTo extends SetGenerator
{
	public static AttachedTo instance(SetGenerator what)
	{
		return new AttachedTo(what);
	}

	private final SetGenerator what;

	private AttachedTo(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Set<Integer> attachments = new java.util.HashSet<Integer>();
		for(AttachableTo a: this.what.evaluate(state, thisObject).getAll(AttachableTo.class))
			for(Integer i: a.getAttachments())
				attachments.add(i);
		return IdentifiedWithID.instance(attachments).evaluate(state, thisObject);
	}
}
