package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Obelisk of Grixis")
@ManaCost("3")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class ObeliskofGrixis extends org.rnd.jmagic.cardTemplates.ShardsObelisk
{
	public ObeliskofGrixis(GameState state)
	{
		super(state, "(UBR)");
	}
}
