package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hound of Griselbrand")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.HOUND})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class HoundofGriselbrand extends Card
{
	public HoundofGriselbrand(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Double strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
