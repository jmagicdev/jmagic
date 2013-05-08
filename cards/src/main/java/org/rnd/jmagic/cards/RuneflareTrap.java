package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Runeflare Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RuneflareTrap extends Card
{
	public static final class CardsDrawn extends MaximumPerPlayer.GameObjectsThisTurnCounter
	{
		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == EventType.DRAW_ONE_CARD;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			Set movements = event.getResult(state);
			for(ZoneChange zc: movements.getAll(ZoneChange.class))
			{

				int ownerID = state.<GameObject>get(zc.oldObjectID).ownerID;
				if(this.counts.containsKey(ownerID))
					this.counts.put(ownerID, this.counts.get(ownerID) + 1);
				else
					this.counts.put(ownerID, 1);
			}
		}
	}

	public RuneflareTrap(GameState state)
	{
		super(state);

		// If an opponent drew three or more cards this turn, you may pay (R)
		// rather than pay Runeflare Trap's mana cost.
		state.ensureTracker(new CardsDrawn());
		SetGenerator opponents = OpponentsOf.instance(You.instance());
		SetGenerator cardsDrawn = MaximumPerPlayer.instance(CardsDrawn.class, opponents);
		SetGenerator trapCondition = Intersect.instance(cardsDrawn, Between.instance(3, null));
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If an opponent drew three or more cards this turn", "(R)"));

		// Runeflare Trap deals damage to target player equal to the number of
		// cards in that player's hand.
		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator amount = Count.instance(InZone.instance(HandOf.instance(targetedBy(target))));
		this.addEffect(spellDealDamage(amount, targetedBy(target), "Runeflare Trap deals damage to target player equal to the number of cards in that player's hand."));
	}
}
