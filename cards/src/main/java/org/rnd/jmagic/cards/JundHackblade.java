package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Jund Hackblade")
@ManaCost("(BG)R")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.GOBLIN})
@ColorIdentity({Color.BLACK, Color.RED, Color.GREEN})
public final class JundHackblade extends AlaraRebornBlade
{
	public JundHackblade(GameState state)
	{
		super(state, org.rnd.jmagic.abilities.keywords.Haste.class);
	}
}
