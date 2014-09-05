package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rhox War Monk")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.RHINO})
@ManaCost("GWU")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class RhoxWarMonk extends Card
{
	public RhoxWarMonk(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
