package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Akrasan Squire")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AkrasanSquire extends Card
{
	public AkrasanSquire(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
