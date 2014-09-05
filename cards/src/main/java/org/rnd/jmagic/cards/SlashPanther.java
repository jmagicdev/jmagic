package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Slash Panther")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("4(R/P)")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SlashPanther extends Card
{
	public SlashPanther(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
