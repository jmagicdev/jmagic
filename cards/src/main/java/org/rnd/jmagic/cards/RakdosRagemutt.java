package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rakdos Ragemutt")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND, SubType.ELEMENTAL})
@ManaCost("3BR")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosRagemutt extends Card
{
	public RakdosRagemutt(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Lifelink, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
