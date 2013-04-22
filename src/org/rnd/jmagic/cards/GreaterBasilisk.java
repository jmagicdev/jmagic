package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Greater Basilisk")
@Types({Type.CREATURE})
@SubTypes({SubType.BASILISK})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GreaterBasilisk extends Card
{
	public GreaterBasilisk(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
