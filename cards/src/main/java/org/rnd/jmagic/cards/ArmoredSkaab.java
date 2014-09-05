package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Armored Skaab")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.WARRIOR})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ArmoredSkaab extends Card
{
	public static final class ArmoredSkaabAbility0 extends EventTriggeredAbility
	{
		public ArmoredSkaabAbility0(GameState state)
		{
			super(state, "When Armored Skaab enters the battlefield, put the top four cards of your library into your graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.addEffect(millCards(You.instance(), 4, "Put the top four cards of your library into your graveyard."));
		}
	}

	public ArmoredSkaab(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// When Armored Skaab enters the battlefield, put the top four cards of
		// your library into your graveyard.
		this.addAbility(new ArmoredSkaabAbility0(state));
	}
}
