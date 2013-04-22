package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Ondu Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.DRUID})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class OnduGiant extends Card
{
	public static final class OnduGiantAbility0 extends EventTriggeredAbility
	{
		public OnduGiantAbility0(GameState state)
		{
			super(state, "When Ondu Giant enters the battlefield, you may search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public OnduGiant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// When Ondu Giant enters the battlefield, you may search your library
		// for a basic land card, put it onto the battlefield tapped, then
		// shuffle your library.
		this.addAbility(new OnduGiantAbility0(state));
	}
}
