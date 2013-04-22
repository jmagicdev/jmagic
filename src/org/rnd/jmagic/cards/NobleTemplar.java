package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Noble Templar")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN, SubType.CLERIC})
@ManaCost("5W")
@Printings({@Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class NobleTemplar extends Card
{
	public NobleTemplar(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(6);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Plainscycling (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.PlainsCycling(state, "2"));
	}
}
