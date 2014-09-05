package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Naya Hushblade")
@ManaCost("(RW)G")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.ROGUE})
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class NayaHushblade extends AlaraRebornBlade
{
	public NayaHushblade(GameState state)
	{
		super(state, org.rnd.jmagic.abilities.keywords.Shroud.class);
	}
}
