package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Thraben Militia")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ThrabenMilitia extends AlternateCard
{
	public ThrabenMilitia(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		this.setColorIndicator(Color.WHITE);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
