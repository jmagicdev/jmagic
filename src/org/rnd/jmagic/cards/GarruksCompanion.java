package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Garruk's Companion")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GarruksCompanion extends Card
{
	public GarruksCompanion(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Trample (If this creature would assign enough damage to its blockers
		// to destroy them, you may have it assign the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
