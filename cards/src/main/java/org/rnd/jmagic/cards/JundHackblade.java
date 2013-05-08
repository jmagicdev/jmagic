package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Jund Hackblade")
@ManaCost("(BG)R")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.GOBLIN})
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class JundHackblade extends AlaraRebornBlade
{
	public JundHackblade(GameState state)
	{
		super(state, org.rnd.jmagic.abilities.keywords.Haste.class);
	}
}
