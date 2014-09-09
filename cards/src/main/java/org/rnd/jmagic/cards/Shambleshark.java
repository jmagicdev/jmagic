package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Shambleshark")
@Types({Type.CREATURE})
@SubTypes({SubType.FISH, SubType.CRAB})
@ManaCost("GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class Shambleshark extends Card
{
	public Shambleshark(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));
	}
}
