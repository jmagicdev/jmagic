package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Baloth Cage Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BalothCageTrap extends Card
{
	public static final class ArtifactsPutOntoTheBattlefieldThisTurnCounter extends MaximumPerPlayer.GameObjectsThisTurnCounter
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
				if(!object.getTypes().contains(Type.ARTIFACT))
					continue;

				return true;
			}
			return false;
		}
	}

	public BalothCageTrap(GameState state)
	{
		super(state);

		// If an opponent had an artifact enter the battlefield under his or her
		// control this turn, you may pay (1)(G) rather than pay Baloth Cage
		// Trap's mana cost.
		state.ensureTracker(new ArtifactsPutOntoTheBattlefieldThisTurnCounter());
		SetGenerator opponents = OpponentsOf.instance(You.instance());
		SetGenerator maxPerOpponent = MaximumPerPlayer.instance(ArtifactsPutOntoTheBattlefieldThisTurnCounter.class, opponents);
		SetGenerator trapCondition = Intersect.instance(Between.instance(1, null), maxPerOpponent);
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If an opponent had an artifact enter the battlefield under his or her control this turn", "(1)(G)"));

		// Put a 4/4 green Beast creature token onto the battlefield.
		CreateTokensFactory token = new CreateTokensFactory(1, 4, 4, "Put a 4/4 green Beast creature token onto the battlefield.");
		token.setColors(Color.GREEN);
		token.setSubTypes(SubType.BEAST);
		this.addEffect(token.getEventFactory());
	}
}
