package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Grim Monolith")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = UrzasLegacy.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class GrimMonolith extends Card
{
	public static final class GrimMonolithAbility2 extends ActivatedAbility
	{
		public GrimMonolithAbility2(GameState state)
		{
			super(state, "(4): Untap Grim Monolith.");
			this.setManaCost(new ManaPool("(4)"));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Grim Monolith."));
		}
	}

	public GrimMonolith(GameState state)
	{
		super(state);

		// Grim Monolith doesn't untap during your untap step.
		this.addAbility(new org.rnd.jmagic.abilities.DoesntUntapDuringYourUntapStep(state, "Grim Monolith"));

		// (T): Add (3) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(3)"));

		// (4): Untap Grim Monolith.
		this.addAbility(new GrimMonolithAbility2(state));
	}
}
