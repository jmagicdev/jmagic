package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tidehollow Strix")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.BIRD})
@ManaCost("UB")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class TidehollowStrix extends Card
{
	public TidehollowStrix(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
