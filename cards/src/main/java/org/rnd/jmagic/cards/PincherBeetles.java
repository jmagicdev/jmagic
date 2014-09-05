package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Pincher Beetles")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class PincherBeetles extends Card
{
	public PincherBeetles(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));
	}
}
