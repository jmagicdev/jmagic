package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Paladin en-Vec")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class PaladinenVec extends Card
{
	public PaladinenVec(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasColor.instance(Color.BLACK, Color.RED), "black and from red"));
	}
}
