package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class ManaTypeOf extends SetGenerator
{
	public static ManaTypeOf instance(SetGenerator what)
	{
		return new ManaTypeOf(what);
	}

	private SetGenerator what;

	private ManaTypeOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(ManaSymbol symbol: this.what.evaluate(state, thisObject).getAll(ManaSymbol.class))
			ret.addAll(symbol.getManaTypes());
		return ret;
	}

	@Override
	public java.util.Set<ManaSymbol.ManaType> extractColors(Game game, GameObject thisObject, java.util.Set<SetGenerator> ignoreThese) throws NoSuchMethodException
	{
		return this.evaluate(game, thisObject).getAll(ManaSymbol.ManaType.class);
	}
}
