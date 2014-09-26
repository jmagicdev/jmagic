package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cabal Ritual")
@Types({Type.INSTANT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class CabalRitual extends Card
{
	public CabalRitual(GameState state)
	{
		super(state);

		// Add (B)(B)(B) to your mana pool.
		// Add (B)(B)(B)(B)(B) to your mana pool instead if seven or more cards
		// are in your graveyard.
		SetGenerator mana = IfThenElse.instance(Threshold.instance(), Identity.fromCollection(new ManaPool("BBBBB")), Identity.fromCollection(new ManaPool("BBB")));
		this.addEffect(addManaToYourManaPoolFromSpell(mana, "Add (B)(B)(B) to your mana pool.\n\nThreshold \u2014 Add (B)(B)(B)(B)(B) to your mana pool instead if seven or more cards are in your graveyard."));
	}
}
