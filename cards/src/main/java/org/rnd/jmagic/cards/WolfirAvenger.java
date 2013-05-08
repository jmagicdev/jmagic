package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wolfir Avenger")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF, SubType.WARRIOR})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class WolfirAvenger extends Card
{
	public WolfirAvenger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// (1)(G): Regenerate Wolfir Avenger.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(1)(G)", this.getName()));
	}
}
