package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/** "You may pay [cost] rather than pay this card's mana cost." */
public final class AlternateCost extends StaticAbility
{
	private final String abilityText;
	private final CostCollection cost;

	public AlternateCost(GameState state, String abilityText, EventFactory... costEvents)
	{
		super(state, abilityText);
		this.abilityText = abilityText;

		CostCollection cost = new CostCollection(CostCollection.TYPE_ALTERNATE, costEvents);
		this.cost = cost;
		this.initialize(cost);
	}

	public AlternateCost(GameState state, String abilityText, CostCollection cost)
	{
		super(state, abilityText);
		this.abilityText = abilityText;
		this.cost = cost;
		this.initialize(cost);
	}

	private void initialize(CostCollection cost)
	{
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
		part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(cost));
		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());

		this.addEffectPart(part);

		this.canApply = NonEmpty.instance();
	}

	@Override
	public AlternateCost create(Game game)
	{
		return new AlternateCost(game.physicalState, this.abilityText, this.cost);
	}
}