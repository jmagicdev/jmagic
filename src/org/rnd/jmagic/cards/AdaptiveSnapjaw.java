package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Adaptive Snapjaw")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD, SubType.BEAST})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class AdaptiveSnapjaw extends Card
{
	public AdaptiveSnapjaw(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(2);

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));
	}
}
