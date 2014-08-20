package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each GameObject which has an instance of the given Keyword
 * ability
 */
public class HasKeywordAbility extends SetGenerator
{
	/**
	 * @param what This will be cast to an array of
	 * {@code Class<? extends Keyword>}, so please make sure that's what it
	 * actually is.
	 */
	@SuppressWarnings("unchecked")
	public static HasKeywordAbility instance(Class<?>... what)
	{
		return new HasKeywordAbility((Class<? extends Keyword>[])what);
	}

	private final Class<? extends Keyword>[] abilities;

	@SafeVarargs
	private HasKeywordAbility(Class<? extends Keyword>... abilities)
	{
		this.abilities = abilities;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.getAllObjects())
			for(Class<? extends Keyword> c: this.abilities)
				if(object.hasAbility(c))
					ret.add(object);

		for(Player player: state.players)
			for(Class<? extends Keyword> c: this.abilities)
				if(player.hasAbility(c))
					ret.add(player);

		return ret;
	}
}
