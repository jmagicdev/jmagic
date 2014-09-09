package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Valeron Outlander")
@ManaCost("GW")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class ValeronOutlander extends org.rnd.jmagic.cardTemplates.Outlander
{
	public ValeronOutlander(GameState state)
	{
		super(state, Color.BLACK);
	}
}
