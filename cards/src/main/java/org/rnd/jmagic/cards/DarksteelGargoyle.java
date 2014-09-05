package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Darksteel Gargoyle")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GARGOYLE})
@ManaCost("7")
@Printings({@Printings.Printed(ex = Darksteel.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DarksteelGargoyle extends Card
{
	public DarksteelGargoyle(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

	}
}
