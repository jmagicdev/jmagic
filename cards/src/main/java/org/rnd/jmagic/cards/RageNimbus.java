package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rage Nimbus")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class RageNimbus extends Card
{
	public static final class RageNimbusAbility1 extends ActivatedAbility
	{
		public RageNimbusAbility1(GameState state)
		{
			super(state, "(1)(R): Target creature attacks this turn if able.");
			this.setManaCost(new ManaPool("(1)(R)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, target);
			this.addEffect(createFloatingEffect("Target creature attacks this turn if able.", part));
		}
	}

	public RageNimbus(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Defender, flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(R): Target creature attacks this turn if able.
		this.addAbility(new RageNimbusAbility1(state));
	}
}
