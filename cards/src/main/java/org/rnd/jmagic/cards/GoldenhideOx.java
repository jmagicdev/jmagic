package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goldenhide Ox")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.OX})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class GoldenhideOx extends Card
{
	public static final class GoldenhideOxAbility0 extends EventTriggeredAbility
	{
		public GoldenhideOxAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Goldenhide Ox or another enchantment enters the battlefield under your control, target creature must be blocked this turn if able.");
			this.addPattern(constellation());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, target);
			this.addEffect(createFloatingEffect("Target creature must be blocked this turn if able.", part));
		}
	}

	public GoldenhideOx(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Constellation \u2014 Whenever Goldenhide Ox or another enchantment
		// enters the battlefield under your control, target creature must be
		// blocked this turn if able.
		this.addAbility(new GoldenhideOxAbility0(state));
	}
}
