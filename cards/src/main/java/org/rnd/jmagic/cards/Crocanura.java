package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Crocanura")
@Types({Type.CREATURE})
@SubTypes({SubType.FROG, SubType.CROCODILE})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Crocanura extends Card
{
	public Crocanura(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));
	}
}
