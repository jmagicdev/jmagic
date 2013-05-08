package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Outrider of Jhess")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class OutriderofJhess extends Card
{
	public OutriderofJhess(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
