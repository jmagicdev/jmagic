package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Waveskimmer Aven")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.SOLDIER})
@ManaCost("2GWU")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class WaveskimmerAven extends Card
{
	public WaveskimmerAven(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
