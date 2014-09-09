package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vorapede")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2GGG")
@ColorIdentity({Color.GREEN})
public final class Vorapede extends Card
{
	public Vorapede(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Vigilance, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
