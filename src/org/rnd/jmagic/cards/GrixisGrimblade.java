package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Grixis Grimblade")
@ManaCost("(UR)B")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ZOMBIE})
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class GrixisGrimblade extends AlaraRebornBlade
{
	public GrixisGrimblade(GameState state)
	{
		super(state, org.rnd.jmagic.abilities.keywords.Deathtouch.class);
	}
}
