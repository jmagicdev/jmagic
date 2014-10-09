package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ravenous Leucrocota")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class RavenousLeucrocota extends Card
{
	public RavenousLeucrocota(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// (6)(G): Monstrosity 3. (If this creature isn't monstrous, put three
		// +1/+1 counters on it and it becomes monstrous.)
		this.addAbility(new org.rnd.jmagic.abilities.Monstrosity(state, "(6)(G)", 3));
	}
}
