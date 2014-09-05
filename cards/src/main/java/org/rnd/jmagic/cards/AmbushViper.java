package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ambush Viper")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class AmbushViper extends Card
{
	public AmbushViper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
