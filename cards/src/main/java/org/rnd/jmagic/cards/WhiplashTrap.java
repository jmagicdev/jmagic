package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Whiplash Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class WhiplashTrap extends Card
{
	public static final class CreaturesPutOntoTheBattlefieldThisTurnCounter extends MaximumPerPlayer.GameObjectsThisTurnCounter
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
				if(!object.getTypes().contains(Type.CREATURE))
					continue;

				return true;
			}
			return false;
		}
	}

	public WhiplashTrap(GameState state)
	{
		super(state);

		// If an opponent had two or more creatures enter the battlefield under
		// his or her control this turn, you may pay (U) rather than pay
		// Whiplash Trap's mana cost.
		// TODO : This is wrong. The card needs to track creatures that enter
		// the battlefield; it's currently looking at things that are currently
		// creatures and have entered the battlefield this turn.
		state.ensureTracker(new CreaturesPutOntoTheBattlefieldThisTurnCounter());
		SetGenerator opponents = OpponentsOf.instance(You.instance());
		SetGenerator maxPerOpponent = MaximumPerPlayer.instance(CreaturesPutOntoTheBattlefieldThisTurnCounter.class, opponents);
		SetGenerator trapCondition = Intersect.instance(Between.instance(2, null), maxPerOpponent);
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If an opponent had two or more creatures enter the battlefield under his or her control this turn", "(U)"));

		// Return two target creatures to their owners' hands.
		Target targets = this.addTarget(CreaturePermanents.instance(), "two target creatures");
		targets.setSingleNumber(numberGenerator(2));

		this.addEffect(bounce(targetedBy(targets), "Return two target creatures to their owners' hands."));
	}
}
