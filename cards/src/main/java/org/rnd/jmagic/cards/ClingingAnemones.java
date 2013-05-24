package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Clinging Anemones")
@Types({Type.CREATURE})
@SubTypes({SubType.JELLYFISH})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ClingingAnemones extends Card
{
	public ClingingAnemones(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));
	}
}
