package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Thundering Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ThunderingGiant extends Card
{
	public ThunderingGiant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
