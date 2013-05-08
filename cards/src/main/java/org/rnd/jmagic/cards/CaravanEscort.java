package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Caravan Escort")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class CaravanEscort extends Card
{
	public CaravanEscort(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Level up (2) ((2): Put a level counter on this. Level up only as a
		// sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(2)"));

		// LEVEL 1-4
		// 2/2
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 4, 2, 2));

		// LEVEL 5+
		// 5/5
		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 5, 5, 5, "First strike", org.rnd.jmagic.abilities.keywords.FirstStrike.class));
	}
}
