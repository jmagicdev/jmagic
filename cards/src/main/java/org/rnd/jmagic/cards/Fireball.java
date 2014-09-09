package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fireball")
@Types({Type.SORCERY})
@ManaCost("XR")
@ColorIdentity({Color.RED})
public final class Fireball extends Card
{
	// Fireball costs (1) more to cast for each target beyond the first.
	public static final class CostPerTarget extends StaticAbility
	{
		public CostPerTarget(GameState state)
		{
			super(state, "Fireball costs (1) more to cast for each target beyond the first.");

			SetGenerator numTargets = Count.instance(AllTargetsOf.instance(This.instance()));
			SetGenerator additionalCost = Subtract.instance(numTargets, numberGenerator(1));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, additionalCost);
			this.addEffectPart(part);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}

	public Fireball(GameState state)
	{
		super(state);

		// Fireball deals X damage divided evenly, rounded down, among any
		// number of target creatures and/or players.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "any number of target creatures and/or players");
		target.setNumber(0, null);

		SetGenerator targets = targetedBy(target);
		SetGenerator X = ValueOfX.instance(This.instance());
		SetGenerator numTargets = Count.instance(targets);
		SetGenerator amount = DivideBy.instance(X, numTargets, false);

		this.addEffect(spellDealDamage(amount, targets, "Fireball deals X damage divided evenly, rounded down, among any number of target creatures and/or players."));

		this.addAbility(new CostPerTarget(state));
	}
}
