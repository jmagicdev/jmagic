package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Clever Impersonator")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class CleverImpersonator extends Card
{
	public CleverImpersonator(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// You may have Clever Impersonator enter the battlefield as a copy of
		// any nonland permanent on the battlefield.
		SetGenerator nonlandPermanents = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
		org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy factory = new org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy(nonlandPermanents);
		this.addAbility(factory.generateName(this.getName(), "any nonland permanent on the battlefield").getStaticAbility(state));
	}
}
