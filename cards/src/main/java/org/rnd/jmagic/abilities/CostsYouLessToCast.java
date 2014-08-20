package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CostsYouLessToCast extends StaticAbility
{
	private final SetGenerator what;
	private final String reduction;

	public CostsYouLessToCast(GameState state, SetGenerator what, String reduction, String abilityText)
	{
		super(state, abilityText);
		this.what = what;
		this.reduction = reduction;

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(what, ControlledBy.instance(You.instance(), Stack.instance())));
		part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool(reduction)));
		this.addEffectPart(part);
	}

	public CostsYouLessToCast(GameState state, SetGenerator what, String reduction, SetGenerator number, String abilityText)
	{
		super(state, abilityText);
		this.what = what;
		this.reduction = reduction;

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(what, ControlledBy.instance(You.instance(), Stack.instance())));
		part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool(reduction)));
		part.parameters.put(ContinuousEffectType.Parameter.NUMBER, number);
		this.addEffectPart(part);
	}

	@Override
	public CostsYouLessToCast create(Game game)
	{
		return new CostsYouLessToCast(game.physicalState, this.what, this.reduction, this.getName());
	}
}