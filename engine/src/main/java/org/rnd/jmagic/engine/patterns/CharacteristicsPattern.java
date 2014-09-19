package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * Intended for use where you can safely trivialize the "freeze" method of a set
 * pattern, to reduce 9 lines of anonymous class to half a line of what you
 * really want via lambdas. See the card Meddling Mage for an example.
 */
public class CharacteristicsPattern implements SetPattern
{
	private final CharacteristicsEvaluatorFunction matcher;
	private final SetGenerator toEvaluate;

	/**
	 * Use this method when you don't need other data off the card. See
	 * Aurelia's Fury.
	 */
	public CharacteristicsPattern(CharacteristicsMatcherFunction matcher)
	{
		this.matcher = (c, set) -> matcher.match(c);
		this.toEvaluate = Empty.instance();
	}

	/**
	 * Use this method when you need outside data, like a named card. See
	 * Meddling Mage.
	 */
	public CharacteristicsPattern(CharacteristicsEvaluatorFunction matcher, SetGenerator toEvaluate)
	{
		this.matcher = matcher;
		this.toEvaluate = toEvaluate;
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		Set evaluation = toEvaluate.evaluate(state, thisObject);
		return set.getAll(Characteristics.class).stream()//
		.anyMatch(c -> this.matcher.match(c, evaluation));
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		// no freezing required (that's the point)
	}

}
