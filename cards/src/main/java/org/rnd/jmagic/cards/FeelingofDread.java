package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Feeling of Dread")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class FeelingofDread extends Card
{
	public FeelingofDread(GameState state)
	{
		super(state);

		// Tap up to two target creatures.
		Target targets = this.addTarget(CreaturePermanents.instance(), "up to two target creatures");
		targets.setNumber(0, 2);
		this.addEffect(tap(targetedBy(targets), "Tap up to two target creatures"));

		// Flashback (1)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(1)(U)"));
	}
}
