package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Crystalline Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class CrystallineSliver extends Card
{
	public CrystallineSliver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// All Slivers have shroud.
		this.addAbility(new org.rnd.jmagic.abilities.AllSliversHave(state, org.rnd.jmagic.abilities.keywords.Shroud.class, "All Slivers have shroud."));
	}
}
