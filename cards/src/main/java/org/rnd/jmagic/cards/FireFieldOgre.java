package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fire-Field Ogre")
@Types({Type.CREATURE})
@SubTypes({SubType.MUTANT, SubType.OGRE})
@ManaCost("1UBR")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class FireFieldOgre extends Card
{
	public FireFieldOgre(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(U)(B)(R)"));
	}
}
