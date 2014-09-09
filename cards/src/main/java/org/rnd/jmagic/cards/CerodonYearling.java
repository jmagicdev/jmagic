package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Cerodon Yearling")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("RW")
@ColorIdentity({Color.WHITE, Color.RED})
public final class CerodonYearling extends Card
{
	public CerodonYearling(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
