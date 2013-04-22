package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kessig Recluse")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class KessigRecluse extends Card
{
	public KessigRecluse(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
