package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ikiral Outrider")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class IkiralOutrider extends Card
{
	public IkiralOutrider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Level up (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(4)"));

		// LEVEL 1-3
		// 2/6
		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 3, 2, 6, "Vigilance", org.rnd.jmagic.abilities.keywords.Vigilance.class));

		// LEVEL 4+
		// 3/10
		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 4, 3, 10, "Vigilance", org.rnd.jmagic.abilities.keywords.Vigilance.class));
	}
}
