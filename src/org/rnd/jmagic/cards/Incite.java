package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Incite")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Incite extends Card
{
	public Incite(GameState state)
	{
		super(state);

		// Target creature becomes red until end of turn and attacks this turn
		// if able.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		ContinuousEffect.Part red = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
		red.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
		red.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.RED));

		ContinuousEffect.Part attacks = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
		attacks.parameters.put(ContinuousEffectType.Parameter.ATTACKING, target);

		this.addEffect(createFloatingEffect("Target creature becomes red until end of turn and attacks this turn if able.", red, attacks));
	}
}
