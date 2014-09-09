package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Archweaver")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("5GG")
@ColorIdentity({Color.GREEN})
public final class Archweaver extends Card
{
	public Archweaver(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Reach, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
