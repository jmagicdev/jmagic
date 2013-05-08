package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the Event, damage, or ZoneChange each given ReplacementEffect is
 * replacing
 */
public class ReplacedBy extends SetGenerator
{
	public static ReplacedBy instance(SetGenerator what)
	{
		return new ReplacedBy(what);
	}

	private final SetGenerator effect;

	private ReplacedBy(SetGenerator effect)
	{
		this.effect = effect;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		Set evaluation = this.effect.evaluate(state, thisObject);

		for(EventReplacementEffect e: evaluation.getAll(EventReplacementEffect.class))
			ret.add(e.isReplacing());

		for(DamageReplacementEffect e: evaluation.getAll(DamageReplacementEffect.class))
			ret.addAll(e.isReplacing);

		for(ZoneChangeReplacementEffect e: evaluation.getAll(ZoneChangeReplacementEffect.class))
			ret.addAll(e.isReplacing);

		return ret;
	}
}
