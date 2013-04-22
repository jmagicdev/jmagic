package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Talisman of Dominance")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class TalismanofDominance extends Card
{
	public TalismanofDominance(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T): Add (U) or (B) to your mana pool. Talisman of Dominance deals 1
		// damage to you.
		this.addAbility(new org.rnd.jmagic.abilities.TapForManaPain(state, this.getName(), "UB"));
	}
}
