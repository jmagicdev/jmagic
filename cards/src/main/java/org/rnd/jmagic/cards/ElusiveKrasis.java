package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Elusive Krasis")
@Types({Type.CREATURE})
@SubTypes({SubType.MUTANT, SubType.FISH})
@ManaCost("1GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class ElusiveKrasis extends Card
{
	public ElusiveKrasis(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Elusive Krasis is unblockable.
		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, this.getName()));

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));
	}
}
