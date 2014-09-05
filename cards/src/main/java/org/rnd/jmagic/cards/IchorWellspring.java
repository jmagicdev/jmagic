package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ichor Wellspring")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class IchorWellspring extends Card
{
	public static final class IchorWellspringAbility0 extends EventTriggeredAbility
	{
		public IchorWellspringAbility0(GameState state)
		{
			super(state, "When Ichor Wellspring enters the battlefield or is put into a graveyard from the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public IchorWellspring(GameState state)
	{
		super(state);

		// When Ichor Wellspring enters the battlefield or is put into a
		// graveyard from the battlefield, draw a card.
		this.addAbility(new IchorWellspringAbility0(state));
	}
}
