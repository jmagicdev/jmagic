package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Purify the Grave")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class PurifytheGrave extends Card
{
	public PurifytheGrave(GameState state)
	{
		super(state);

		// Exile target card from a graveyard.
		SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card in a graveyard"));
		this.addEffect(exile(target, "Exile target card from a graveyard."));

		// Flashback (W) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(W)"));
	}
}
