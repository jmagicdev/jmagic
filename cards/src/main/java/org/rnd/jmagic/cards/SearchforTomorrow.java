package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Search for Tomorrow")
@Types({Type.SORCERY})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SearchforTomorrow extends Card
{
	public SearchforTomorrow(GameState state)
	{
		super(state);

		this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(false));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Suspend(state, 2, "(G)"));
	}
}
