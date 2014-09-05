package org.rnd.jmagic.abilityTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Strive extends StaticAbility
{
	public Strive(GameState state, String objectName, String cost)
	{
		super(state, objectName + " costs " + cost + " more to cast for each target beyond the first.");

		SetGenerator numTargets = Count.instance(AllTargetsOf.instance(This.instance()));
		SetGenerator costMultiplier = Subtract.instance(numTargets, numberGenerator(1));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
		part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool(cost)));
		part.parameters.put(ContinuousEffectType.Parameter.NUMBER, costMultiplier);
		this.addEffectPart(part);

		this.canApply = THIS_IS_ON_THE_STACK;
	}
}