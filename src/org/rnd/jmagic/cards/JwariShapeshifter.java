package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jwari Shapeshifter")
@Types({Type.CREATURE})
@SubTypes({SubType.ALLY, SubType.SHAPESHIFTER})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class JwariShapeshifter extends Card
{
	public JwariShapeshifter(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// You may have Jwari Shapeshifter enter the battlefield as a copy of
		// any Ally creature on the battlefield.
		SetGenerator allies = Intersect.instance(HasSubType.instance(SubType.ALLY), CreaturePermanents.instance());
		this.addAbility(new org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy(allies).generateName(this.getName(), "any Ally creature on the battlefield").getStaticAbility(state));
	}
}
