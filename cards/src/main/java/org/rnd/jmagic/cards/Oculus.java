package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Oculus")
@Types({Type.CREATURE})
@SubTypes({SubType.HOMUNCULUS})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Oculus extends Card
{
	public static final class OculusAbility0 extends EventTriggeredAbility
	{
		public OculusAbility0(GameState state)
		{
			super(state, "When Oculus dies, you may draw a card.");
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());
			this.addEffect(youMay(drawACard(), "You may draw a card."));
		}
	}

	public Oculus(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Oculus is put into a graveyard from the battlefield, you may
		// draw a card.
		this.addAbility(new OculusAbility0(state));
	}
}
