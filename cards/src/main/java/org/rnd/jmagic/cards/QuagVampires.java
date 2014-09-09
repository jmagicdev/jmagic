package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Quag Vampires")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.ROGUE})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class QuagVampires extends Card
{
	public QuagVampires(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Multikicker (1)(B) (You may pay an additional (1)(B) any number of
		// times as you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(1)(B)");
		this.addAbility(kicker);

		// Swampwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));

		// Quag Vampires enters the battlefield with a +1/+1 counter on it for
		// each time it was kicked.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithAPlusOnePlusOneCounterForEachTimeItWasKicked(state, "Quag Vampires", kicker.costCollections[0]));
	}
}
