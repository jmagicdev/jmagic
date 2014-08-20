package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all the mana in the given players' mana pools, and all the mana
 * in the given mana pools.
 */
public class ManaInPool extends SetGenerator
{
	public static ManaInPool instance(SetGenerator what)
	{
		return new ManaInPool(what);
	}

	private SetGenerator what;

	private ManaInPool(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set what = this.what.evaluate(state, thisObject);
		Set ret = new Set();
		for(Player player: what.getAll(Player.class))
			ret.addAll(player.pool);
		for(ManaPool pool: what.getAll(ManaPool.class))
			ret.addAll(pool);
		return ret;
	}

	@Override
	public java.util.Set<ManaSymbol.ManaType> extractColors(Game game, GameObject thisObject, java.util.Set<SetGenerator> ignoreThese) throws NoSuchMethodException
	{
		return Identity.fromCollection(this.evaluate(game, thisObject)).extractColors(game, thisObject, ignoreThese);
	}
}
