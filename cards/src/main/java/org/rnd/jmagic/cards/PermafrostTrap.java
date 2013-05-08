package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Permafrost Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class PermafrostTrap extends Card
{
	public static final class GreenCreaturesPutOntoTheBattlefieldThisTurnCounter extends MaximumPerPlayer.GameObjectsThisTurnCounter
	{
		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type != EventType.MOVE_BATCH)
				return false;

			for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
			{
				if(state.battlefield().ID != change.destinationZoneID)
					continue;

				GameObject object = state.get(change.newObjectID);
				if(!object.getColors().contains(Color.GREEN) || !object.getTypes().contains(Type.CREATURE))
					continue;

				return true;
			}
			return false;
		}
	}

	public PermafrostTrap(GameState state)
	{
		super(state);

		// If an opponent had a green creature enter the battlefield under his
		// or her control this turn, you may pay (U) rather than pay Permafrost
		// Trap's mana cost.
		state.ensureTracker(new GreenCreaturesPutOntoTheBattlefieldThisTurnCounter());
		SetGenerator opponents = OpponentsOf.instance(You.instance());
		SetGenerator maxPerOpponent = MaximumPerPlayer.instance(GreenCreaturesPutOntoTheBattlefieldThisTurnCounter.class, opponents);
		SetGenerator trapCondition = Intersect.instance(Between.instance(2, null), maxPerOpponent);
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If an opponent had a green creature enter the battlefield under his or her control this turn", "(U)"));

		Target target = this.addTarget(CreaturePermanents.instance(), "up to two target creatures");
		target.setNumber(0, 2);

		// Tap up to two target creatures. Those creatures don't untap during
		// their controller's next untap step.
		EventFactory tap = new EventFactory(EventType.TAP_HARD, "Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.");
		tap.parameters.put(EventType.Parameter.CAUSE, This.instance());
		tap.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(tap);
	}
}
