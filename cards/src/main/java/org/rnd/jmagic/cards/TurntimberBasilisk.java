package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Turntimber Basilisk")
@Types({Type.CREATURE})
@SubTypes({SubType.BASILISK})
@ManaCost("1GG")
@ColorIdentity({Color.GREEN})
public final class TurntimberBasilisk extends Card
{
	public static final class LandfallForcesBlock extends EventTriggeredAbility
	{
		public LandfallForcesBlock(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may have target creature block Turntimber Basilisk this turn if able.");
			this.addPattern(landfall());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, targetedBy(target));

			this.addEffect(youMay(createFloatingEffect("Target creature blocks Turntimber Basilisk this turn if able.", part), "You may have target creature block Turntimber Basilisk this turn if able."));
		}
	}

	public TurntimberBasilisk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Deathtouch (Creatures dealt damage by this creature are destroyed.
		// You can divide this creature's combat damage among any of the
		// creatures blocking or blocked by it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may have target creature block Turntimber Basilisk this
		// turn if able.
		this.addAbility(new LandfallForcesBlock(state));
	}
}
