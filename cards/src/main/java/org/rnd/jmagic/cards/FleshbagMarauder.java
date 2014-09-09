package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fleshbag Marauder")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class FleshbagMarauder extends Card
{
	public static final class InnocentBlood extends EventTriggeredAbility
	{
		public InnocentBlood(GameState state)
		{
			super(state, "When Fleshbag Marauder enters the battlefield, each player sacrifices a creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(sacrifice(Players.instance(), 1, CreaturePermanents.instance(), "Each player sacrifices a creature."));
		}
	}

	public FleshbagMarauder(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// When Fleshbag Marauder enters the battlefield, each player sacrifices
		// a creature.
		this.addAbility(new InnocentBlood(state));
	}
}
