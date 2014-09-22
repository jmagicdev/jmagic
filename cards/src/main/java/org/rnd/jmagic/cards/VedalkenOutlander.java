package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vedalken Outlander")
@ManaCost("WU")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.VEDALKEN, SubType.SCOUT})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class VedalkenOutlander extends org.rnd.jmagic.cardTemplates.Outlander
{
	public VedalkenOutlander(GameState state)
	{
		super(state, Color.RED);
	}
}
