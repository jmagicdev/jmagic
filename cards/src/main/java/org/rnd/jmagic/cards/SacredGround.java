package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sacred Ground")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Stronghold.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SacredGround extends Card
{
	public static final class SacredGroundAbility0 extends EventTriggeredAbility
	{
		public SacredGroundAbility0(GameState state)
		{
			super(state, "Whenever a spell or ability an opponent controls causes a land to be put into your graveyard from the battlefield, return that card to the battlefield.");

			ZoneChangePattern pattern = new ZoneChangePattern()
			{
				@Override
				public boolean looksBackInTime()
				{
					return true;
				}

				@Override
				public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state)
				{
					// a spell or ability an opponent controls causes
					if(zoneChange.causeID == -1)
						return false;
					Player causeController = state.<GameObject>get(zoneChange.causeID).getController(state);
					Player you = ((GameObject)thisObject).getController(state);
					if(!OpponentsOf.get(state, you).contains(causeController))
						return false;

					// a land
					GameObject movedObject = state.get(zoneChange.oldObjectID);
					if(!movedObject.getTypes().contains(Type.LAND))
						return false;

					// to be put into your graveyard
					if(zoneChange.destinationZoneID != you.getGraveyardID())
						return false;

					// from the battlefield
					if(zoneChange.sourceZoneID != state.battlefield().ID)
						return false;

					return true;
				}
			};
			this.addPattern(pattern);

			SetGenerator thatCard = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return that card to the battlefield.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.OBJECT, thatCard);
			move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(move);
		}
	}

	public SacredGround(GameState state)
	{
		super(state);

		// Whenever a spell or ability an opponent controls causes a land to be
		// put into your graveyard from the battlefield, return that card to the
		// battlefield.
		this.addAbility(new SacredGroundAbility0(state));
	}
}
