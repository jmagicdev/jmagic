package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Risen Sanctuary")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("5GW")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class RisenSanctuary extends Card
{
	public RisenSanctuary(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
