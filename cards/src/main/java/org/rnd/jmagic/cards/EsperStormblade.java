package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Esper Stormblade")
@ManaCost("(WB)U")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.VEDALKEN})
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class EsperStormblade extends AlaraRebornBlade
{
	public EsperStormblade(GameState state)
	{
		super(state, org.rnd.jmagic.abilities.keywords.Flying.class);
	}
}
