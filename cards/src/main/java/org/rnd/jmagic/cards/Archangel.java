package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Archangel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WW")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.RARE), @Printings.Printed(ex = Portal.class, r = Rarity.RARE), @Printings.Printed(ex = Visions.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Archangel extends Card
{
	public Archangel(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
