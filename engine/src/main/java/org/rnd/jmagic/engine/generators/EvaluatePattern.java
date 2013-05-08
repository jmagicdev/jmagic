package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class EvaluatePattern extends SetGenerator
{
	public static EvaluatePattern instance(SetPattern pattern, SetGenerator what)
	{
		return new EvaluatePattern(pattern, what);
	}

	private SetPattern pattern;
	private SetGenerator what;

	private EvaluatePattern(SetPattern pattern, SetGenerator what)
	{
		this.pattern = pattern;
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(this.pattern.match(state, thisObject, this.what.evaluate(state, thisObject)))
			return NonEmpty.set;
		return Empty.set;
	}

}
