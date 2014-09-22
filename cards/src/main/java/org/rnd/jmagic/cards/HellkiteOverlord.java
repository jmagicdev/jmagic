package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hellkite Overlord")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4BRRG")
@ColorIdentity({Color.BLACK, Color.RED, Color.GREEN})
public final class HellkiteOverlord extends Card
{
	public HellkiteOverlord(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Flying, trample, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// (R): Hellkite Overlord gets +1/+0 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, "Hellkite Overlord"));

		// (B)(G): Regenerate Hellkite Overlord.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)(G)", "Hellkite Overlord"));
	}
}
