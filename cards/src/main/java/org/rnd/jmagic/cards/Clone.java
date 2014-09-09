package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Clone")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class Clone extends Card
{
	public Clone(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy(CreaturePermanents.instance()).generateName(this.getName(), "any creature on the battlefield").getStaticAbility(state));
	}
}
