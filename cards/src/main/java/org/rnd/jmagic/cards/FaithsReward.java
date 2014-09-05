package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Faith's Reward")
@Types({Type.INSTANT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class FaithsReward extends Card
{
	public FaithsReward(GameState state)
	{
		super(state);

		// Return to the battlefield all permanent cards in your graveyard that
		// were put there from the battlefield this turn.
		state.ensureTracker(new PutIntoGraveyardsFromBattlefieldThisTurn.DeathTracker());
		SetGenerator permanentTypes = HasType.instance(Type.permanentTypes());
		SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator yourDeadThisTurn = Intersect.instance(permanentTypes, Cards.instance(), inYourGraveyard, PutIntoGraveyardsFromBattlefieldThisTurn.instance());
		this.addEffect(putOntoBattlefield(yourDeadThisTurn, "Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield this turn."));
	}
}
