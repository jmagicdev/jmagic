package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Thundering Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ThunderingGiant extends Card
{
	public ThunderingGiant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
