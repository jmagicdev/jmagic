package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Unburial Rites")
@Types({Type.SORCERY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class UnburialRites extends Card
{
	public UnburialRites(GameState state)
	{
		super(state);

		// Return target creature card from your graveyard to the battlefield.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature card in your graveyard"));
		this.addEffect(putOntoBattlefield(target, "Return target creature card from your graveyard to the battlefield."));

		// Flashback (3)(W) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(3)(W)"));
	}
}
