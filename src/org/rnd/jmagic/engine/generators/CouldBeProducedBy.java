package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the mana types that could be produced by activated abilities of
 * each given GameObject.
 */
public class CouldBeProducedBy extends SetGenerator
{
	public static CouldBeProducedBy instance(SetGenerator what)
	{
		return new CouldBeProducedBy(what);
	}

	private final SetGenerator producers;

	private CouldBeProducedBy(SetGenerator producers)
	{
		this.producers = producers;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return evaluate(state, thisObject, new java.util.HashSet<SetGenerator>());
	}

	public Set evaluate(GameState state, Identified thisObject, java.util.Set<SetGenerator> ignoreThese)
	{
		Set results = this.producers.evaluate(state, thisObject);

		Set ret = new Set();

		ignoreThese.add(this);
		for(GameObject object: results.getAll(GameObject.class))
			ret.addAll(objectCouldProduce(state.game, object, ignoreThese));

		return ret;
	}

	public static java.util.Set<ManaSymbol.ManaType> objectCouldProduce(Game game, GameObject object, java.util.Set<SetGenerator> ignoreThese)
	{
		java.util.Set<ManaSymbol.ManaType> colors = new java.util.HashSet<ManaSymbol.ManaType>();

		for(NonStaticAbility ability: object.getNonStaticAbilities())
		{
			for(Mode mode: ability.getModes())
			{
				for(EventFactory effect: mode.effects)
				{
					if(effect.type.addsMana())
					{
						try
						{
							// NUMBER defaults to one if it isn't defined
							if(!effect.parameters.containsKey(EventType.Parameter.NUMBER) || effect.parameters.get(EventType.Parameter.NUMBER).isGreaterThanZero(game, object))
								colors.addAll(effect.parameters.get(EventType.Parameter.MANA).extractColors(game, object, ignoreThese));
						}
						catch(NoSuchMethodException ex)
						{
							System.err.println(ex);
						}
					}
				}
			}
		}

		return colors;
	}

	@Override
	public java.util.Set<ManaSymbol.ManaType> extractColors(Game game, GameObject thisObject, java.util.Set<SetGenerator> ignoreThese) throws NoSuchMethodException
	{
		// This entire ignoreThese set of parameters is solely for this point,
		// so that ripping apart CouldBeProducedBy won't result in infinite
		// recursion
		if(ignoreThese.contains(this))
			return new java.util.HashSet<ManaSymbol.ManaType>();
		ignoreThese.add(this);
		return this.evaluate(game.actualState, thisObject, ignoreThese).getAll(ManaSymbol.ManaType.class);
	}
}
