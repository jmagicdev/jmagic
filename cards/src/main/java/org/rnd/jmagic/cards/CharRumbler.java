package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Char-Rumbler")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class CharRumbler extends Card
{
	public CharRumbler(GameState state)
	{
		super(state);

		this.setPower(-1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, this.getName()));
	}
}
