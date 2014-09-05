package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Severed Legion")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SeveredLegion extends Card
{
	public SeveredLegion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fear(state));
	}
}
