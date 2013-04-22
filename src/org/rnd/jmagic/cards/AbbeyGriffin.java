package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Abbey Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AbbeyGriffin extends Card
{
	public AbbeyGriffin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
