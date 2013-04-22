package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the unique elements of the first set, compared to the second
 */
public class RelativeComplement extends SetGenerator
{
	public static RelativeComplement instance(SetGenerator a, SetGenerator b)
	{
		return new RelativeComplement(a, b);
	}

	static public Set get(Set a, Set b)
	{
		Set ret = new Set(a);
		ret.removeAll(b);
		return ret;
	}

	private final SetGenerator a;
	private final SetGenerator b;

	private RelativeComplement(SetGenerator a, SetGenerator b)
	{
		this.a = a;
		this.b = b;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return RelativeComplement.get(this.a.evaluate(state, thisObject), this.b.evaluate(state, thisObject));
	}

	@Override
	public java.util.Set<ManaSymbol.ManaType> extractColors(Game game, GameObject thisObject, java.util.Set<SetGenerator> ignoreThese) throws NoSuchMethodException
	{
		java.util.Set<ManaSymbol.ManaType> a = this.a.extractColors(game, thisObject, ignoreThese);
		java.util.Set<ManaSymbol.ManaType> b = this.b.extractColors(game, thisObject, ignoreThese);
		a.removeAll(b);
		return a;
	}
}
