package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Knight of Cliffhaven")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.KOR})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KnightofCliffhaven extends Card
{
	public KnightofCliffhaven(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Level up (3) ((3): Put a level counter on this. Level up only as a
		// sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(3)"));

		// LEVEL 1-3
		// 2/3
		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 3, 2, 3, "Flying", org.rnd.jmagic.abilities.keywords.Flying.class));

		// LEVEL 4+
		// 4/4
		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 4, 4, 4, "Flying; Vigilance", org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.Vigilance.class));
	}
}
