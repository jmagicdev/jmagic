package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Skylasher")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.DRAGONS_MAZE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Skylasher extends Card
{
	public Skylasher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Skylasher can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Reach, protection from blue
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromBlue(state));
	}
}
