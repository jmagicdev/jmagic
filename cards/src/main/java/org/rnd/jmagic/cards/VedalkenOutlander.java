package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vedalken Outlander")
@ManaCost("WU")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.VEDALKEN, SubType.SCOUT})
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class VedalkenOutlander extends org.rnd.jmagic.cardTemplates.Outlander
{
	public VedalkenOutlander(GameState state)
	{
		super(state, Color.RED);
	}
}
