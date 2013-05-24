package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * This essentially represents the ternary operator
 */
public class IfThenElse extends SetGenerator
{
	public static IfThenElse instance(SetGenerator ifCondition, SetGenerator thenSet, SetGenerator elseSet)
	{
		return new IfThenElse(ifCondition, thenSet, elseSet);
	}

	private final SetGenerator ifCondition;
	private final SetGenerator thenSet;
	private final SetGenerator elseSet;

	private IfThenElse(SetGenerator ifCondition, SetGenerator thenSet, SetGenerator elseSet)
	{
		this.ifCondition = ifCondition;
		this.thenSet = thenSet;
		this.elseSet = elseSet;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(this.ifCondition.evaluate(state, thisObject).isEmpty())
			return this.elseSet.evaluate(state, thisObject);
		return this.thenSet.evaluate(state, thisObject);
	}

	@Override
	public java.util.Set<ManaSymbol.ManaType> extractColors(Game game, GameObject thisObject, java.util.Set<SetGenerator> ignoreThese) throws NoSuchMethodException
	{
		if(this.ifCondition.evaluate(game, thisObject).isEmpty())
			return this.elseSet.extractColors(game, thisObject, ignoreThese);
		return this.thenSet.extractColors(game, thisObject, ignoreThese);
	}
}
