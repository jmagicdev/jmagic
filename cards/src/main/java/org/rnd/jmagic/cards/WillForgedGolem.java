package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Will-Forged Golem")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@ColorIdentity({})
public final class WillForgedGolem extends Card
{
	public WillForgedGolem(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));
	}
}
