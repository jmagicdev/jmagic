package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Anaconda")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Portal.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Anaconda extends Card
{
	public Anaconda(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Swampwalk (This creature is unblockable as long as defending player
		// controls a Swamp.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
