package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Viashino Racketeer")
@Types({Type.CREATURE})
@SubTypes({SubType.VIASHINO, SubType.ROGUE})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ViashinoRacketeer extends Card
{
	public static final class ViashinoRacketeerAbility0 extends EventTriggeredAbility
	{
		public ViashinoRacketeerAbility0(GameState state)
		{
			super(state, "When Viashino Racketeer enters the battlefield, you may discard a card. If you do, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory youMayDiscard = youMay(discardCards(You.instance(), 1, "Discard a card"), "You may discard a card.");
			this.addEffect(ifThen(youMayDiscard, drawCards(You.instance(), 1, "Draw a card"), "If you do, draw a card."));
		}
	}

	public ViashinoRacketeer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Viashino Racketeer enters the battlefield, you may discard a
		// card. If you do, draw a card.
		this.addAbility(new ViashinoRacketeerAbility0(state));
	}
}
