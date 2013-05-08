package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the chosen targets of all the given targets on all the given
 * objects. If those targets have not yet been chosen, evaluates to the legal
 * choices for those targets. This always evaluates to {@link Target} objects,
 * not {@link GameObject}s or {@link Player}s (or {@link Zone}s grumble
 * grumble).
 */
public class ChosenTargetsFor extends SetGenerator
{
	public static ChosenTargetsFor instance(SetGenerator targets, SetGenerator what)
	{
		return new ChosenTargetsFor(targets, what);
	}

	private SetGenerator targets;
	private SetGenerator what;

	private ChosenTargetsFor(SetGenerator targets, SetGenerator what)
	{
		this.targets = targets;
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject o: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			for(Target t: this.targets.evaluate(state, thisObject).getAll(Target.class))
			{
				if(o.getChosenTargets().containsKey(t))
				{
					for(Target chosen: o.getChosenTargets().get(t))
						if(chosen != null)
							ret.add(chosen);
				}
				else
				{
					Set legalChoices;
					if(org.rnd.jmagic.abilities.keywords.Overload.WasOverloaded.get(o))
						legalChoices = t.legalChoices.evaluate(state.game, o);
					else
						// This code is hit when Searing Blaze is checking
						// whether its second target can be chosen
						legalChoices = t.legalChoicesNow(state.game, o);
					for(Identified i: legalChoices.getAll(Identified.class))
						ret.add(new Target(i));
				}
			}
		return ret;
	}
}
