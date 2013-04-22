package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Brontotherium")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Brontotherium extends Card
{
	public Brontotherium(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Provoke(state));
	}
}
