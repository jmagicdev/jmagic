package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Viridian Emissary")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SCOUT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ViridianEmissary extends Card
{
	public static final class ViridianEmissaryAbility0 extends EventTriggeredAbility
	{
		public ViridianEmissaryAbility0(GameState state)
		{
			super(state, "When Viridian Emissary dies, you may search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.");
			this.addPattern(whenThisDies());
			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public ViridianEmissary(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Viridian Emissary is put into a graveyard from the battlefield,
		// you may search your library for a basic land card, put it onto the
		// battlefield tapped, then shuffle your library.
		this.addAbility(new ViridianEmissaryAbility0(state));
	}
}
