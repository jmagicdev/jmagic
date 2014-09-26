package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Strive extends StaticAbility
{
	private String objectName;
	private String cost;

	public Strive(GameState state, String objectName, String cost)
	{
		super(state, "Strive \u2014 " + objectName + " costs " + cost + " more to cast for each target beyond the first.");

		this.objectName = objectName;
		this.cost = cost;

		SetGenerator numTargets = Count.instance(AllTargetsOf.instance(This.instance()));
		SetGenerator costMultiplier = Subtract.instance(numTargets, numberGenerator(1));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
		part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool(cost)));
		part.parameters.put(ContinuousEffectType.Parameter.NUMBER, costMultiplier);
		this.addEffectPart(part);

		this.canApply = THIS_IS_ON_THE_STACK;
	}

	@Override
	public Strive create(Game game)
	{
		return new Strive(game.physicalState, this.objectName, this.cost);
	}
}