package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Bant Sureblade")
@ManaCost("(GU)W")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.GREEN})
public final class BantSureblade extends AlaraRebornBlade
{
	public BantSureblade(GameState state)
	{
		super(state, org.rnd.jmagic.abilities.keywords.FirstStrike.class);
	}
}
