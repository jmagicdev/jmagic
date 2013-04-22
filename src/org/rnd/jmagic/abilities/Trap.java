package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Trap extends StaticAbility
{
	private String cardName;
	private SetGenerator trapCondition;
	private String conditionDescription;
	private String trapCost;

	/**
	 * @param state The game state in which to construct this ability.
	 * @param cardName The name of the card this ability is on.
	 * @param trapCondition The condition under which the card can be cast for
	 * its trap cost.
	 * @param conditionDescription A string description for trapCondition,
	 * including the word "if".
	 * @param trapCost The trap cost, with parentheses around each mana symbol.
	 */
	public Trap(GameState state, String cardName, SetGenerator trapCondition, String conditionDescription, String trapCost)
	{
		super(state, conditionDescription + ", you may pay " + trapCost + " rather than pay " + cardName + "'s mana cost.");

		this.cardName = cardName;
		this.trapCondition = trapCondition;
		this.conditionDescription = conditionDescription;
		this.trapCost = trapCost;

		this.canApply = trapCondition;

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);

		part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new CostCollection(CostCollection.TYPE_ALTERNATE, trapCost)));
		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());

		this.addEffectPart(part);
	}

	@Override
	public Trap create(Game game)
	{
		return new Trap(game.physicalState, this.cardName, this.trapCondition, this.conditionDescription, this.trapCost);
	}
}
