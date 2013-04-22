package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Skywatcher Adept")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.WIZARD})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SkywatcherAdept extends Card
{
	public SkywatcherAdept(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Level up (3) ((3): Put a level counter on this. Level up only as a
		// sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(3)"));

		// LEVEL 1-2
		// 2/2
		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 2, 2, 2, "Flying", org.rnd.jmagic.abilities.keywords.Flying.class));

		// LEVEL 3+
		// 4/2
		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 3, 4, 2, "Flying", org.rnd.jmagic.abilities.keywords.Flying.class));
	}
}
