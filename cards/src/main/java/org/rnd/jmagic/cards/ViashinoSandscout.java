package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Viashino Sandscout")
@Types({Type.CREATURE})
@SubTypes({SubType.VIASHINO, SubType.SCOUT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class ViashinoSandscout extends Card
{
	public static final class BounceEOT extends EventTriggeredAbility
	{
		public BounceEOT(GameState state)
		{
			super(state, "At the beginning of the end step, return Viashino Sandscout to its owner's hand.");
			this.addPattern(atTheBeginningOfTheEndStep());

			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Viashino Sandscout to its owner's hand."));
		}
	}

	public ViashinoSandscout(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
		this.addAbility(new BounceEOT(state));
	}
}
