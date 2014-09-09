package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Revenge of the Hunted")
@Types({Type.SORCERY})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class RevengeoftheHunted extends Card
{
	public RevengeoftheHunted(GameState state)
	{
		super(state);

		// Until end of turn, target creature gets +6/+6 and gains trample, and
		// all creatures able to block it this turn do so.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		ContinuousEffect.Part boost = modifyPowerAndToughness(target, +6, +6);
		ContinuousEffect.Part trample = addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.Trample.class);

		ContinuousEffect.Part lure = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT_FOR_EACH_DEFENDING_CREATURE);
		lure.parameters.put(ContinuousEffectType.Parameter.ATTACKING, target);
		lure.parameters.put(ContinuousEffectType.Parameter.DEFENDING, HasType.instance(Type.CREATURE));

		this.addEffect(createFloatingEffect("Until end of turn, target creature gets +6/+6 and gains trample, and all creatures able to block it this turn do so.", boost, trample, lure));

		// Miracle (G)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(G)"));
	}
}
