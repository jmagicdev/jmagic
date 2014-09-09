package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Avalanche Riders")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.NOMAD})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class AvalancheRiders extends Card
{
	public static final class AvalancheRidersAbility2 extends EventTriggeredAbility
	{
		public AvalancheRidersAbility2(GameState state)
		{
			super(state, "When Avalanche Riders enters the battlefield, destroy target land.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
			this.addEffect(destroy(target, "Destroy target land."));
		}
	}

	public AvalancheRiders(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Echo (3)(R) (At the beginning of your upkeep, if this came under your
		// control since the beginning of your last upkeep, sacrifice it unless
		// you pay its echo cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Echo(state, "(3)(R)"));

		// When Avalanche Riders enters the battlefield, destroy target land.
		this.addAbility(new AvalancheRidersAbility2(state));
	}
}
