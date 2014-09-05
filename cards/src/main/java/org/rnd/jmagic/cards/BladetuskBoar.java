package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Bladetusk Boar")
@Types({Type.CREATURE})
@SubTypes({SubType.BOAR})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BladetuskBoar extends Card
{
	public BladetuskBoar(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));
	}
}
