package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ravenous Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class RavenousTrap extends Card
{
	public static final class CardsPutIntoAGraveyardThisTurnCounter extends MaximumPerPlayer.GameObjectsThisTurnCounter
	{
		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type != EventType.MOVE_BATCH)
				return false;

			for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
			{
				if(!state.<Zone>get(change.destinationZoneID).isGraveyard())
					continue;

				if(!state.<GameObject>get(change.newObjectID).isCard())
					continue;

				return true;
			}
			return false;
		}
	}

	public RavenousTrap(GameState state)
	{
		super(state);

		// If an opponent had three or more cards put into his or her graveyard
		// from anywhere this turn, you may pay (0) rather than pay Ravenous
		// Trap's mana cost.
		state.ensureTracker(new CardsPutIntoAGraveyardThisTurnCounter());
		SetGenerator opponents = OpponentsOf.instance(You.instance());
		SetGenerator maxPerOpponent = MaximumPerPlayer.instance(CardsPutIntoAGraveyardThisTurnCounter.class, opponents);
		SetGenerator trapCondition = Intersect.instance(Between.instance(3, null), maxPerOpponent);
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If an opponent had three or more cards put into his or her graveyard from anywhere this turn", "(0)"));

		// Exile all cards from target player's graveyard.
		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(exile(InZone.instance(GraveyardOf.instance(targetedBy(target))), "Exile all cards from target player's graveyard."));
	}
}
