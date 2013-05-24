package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Desperate Ritual")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class DesperateRitual extends Card
{
	public DesperateRitual(GameState state)
	{
		super(state);

		// Add (R)(R)(R) to your mana pool.
		this.addEffect(addManaToYourManaPoolFromSpell("(R)(R)(R)"));

		// Splice onto Arcane (1)(R) (As you cast an Arcane spell, you may
		// reveal this card from your hand and pay its splice cost. If you do,
		// add this card's effects to that spell.)
		this.addAbility(org.rnd.jmagic.abilities.keywords.Splice.ontoArcane(state, "(1)(R)"));
	}
}
