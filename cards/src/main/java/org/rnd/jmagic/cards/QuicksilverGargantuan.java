package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quicksilver Gargantuan")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("5UU")
@ColorIdentity({Color.BLUE})
public final class QuicksilverGargantuan extends Card
{
	public QuicksilverGargantuan(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// You may have Quicksilver Gargantuan enter the battlefield as a copy
		// of any creature on the battlefield, except it's still 7/7.
		this.addAbility(new org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy(CreaturePermanents.instance()).setName("You may have Quicksilver Gargantuan enter the battlefield as a copy of any creature on the battlefield, except it's still 7/7.").retainPowerAndToughness().getStaticAbility(state));
	}
}
