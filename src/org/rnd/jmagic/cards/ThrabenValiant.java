package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Thraben Valiant")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ThrabenValiant extends Card
{
	public ThrabenValiant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
