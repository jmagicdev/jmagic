package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the number of elements in the given set
 */
public class Sunburst extends SetGenerator
{
	private static final SetGenerator MANA_SPENT_ON_THIS = ManaSpentOn.instance(This.instance());
	private static final Sunburst INSTANCE = new Sunburst();

	public static Sunburst instance()
	{
		return INSTANCE;
	}

	private Sunburst()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Set<Color> colors = new java.util.HashSet<Color>();
		for(ManaSymbol s: MANA_SPENT_ON_THIS.evaluate(state, thisObject).getAll(ManaSymbol.class))
			colors.addAll(s.colors);
		return new Set(colors.size());
	}

	@Override
	public boolean isGreaterThanZero(Game game, GameObject thisObject) throws NoSuchMethodException
	{
		for(ManaSymbol s: MANA_SPENT_ON_THIS.evaluate(game.actualState, thisObject).getAll(ManaSymbol.class))
			if(!s.colors.isEmpty())
				return true;
		return false;
	}
}
