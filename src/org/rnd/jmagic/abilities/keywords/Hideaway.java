package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 702.72. Hideaway
 * 
 * 702.72a Hideaway represents a static ability and a triggered ability.
 * "Hideaway" means "This permanent enters the battlefield tapped" and
 * "When this permanent enters the battlefield, look at the top four cards of your library. Exile one of them face down and put the rest on the bottom of your library in any order. The exiled card gains 'Any player who has controlled the permanent that exiled this card may look at this card in the exile zone.'"
 */
public abstract class Hideaway extends Keyword
{
	public Hideaway(GameState state)
	{
		super(state, "Hideaway");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(this.state, "This permanent"));
	}

	public static abstract class Exile extends EventTriggeredAbility
	{
		public static final class ControlledThePermanentThatExiledThis extends SetGenerator
		{
			private static final ControlledThePermanentThatExiledThis INSTANCE = new ControlledThePermanentThatExiledThis();

			public static ControlledThePermanentThatExiledThis instance()
			{
				return INSTANCE;
			}

			private ControlledThePermanentThatExiledThis()
			{
				// Do nothing
			}

			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				Set ret = new Set();

				java.util.Map<Integer, Integer> exiledBy = state.getTracker(ExiledBy.class).getValue(state);
				if(exiledBy.containsKey(thisObject.ID))
				{
					int exilerID = exiledBy.get(thisObject.ID);
					if(0 != exilerID)
					{
						Identified exiler = state.get(exilerID);
						if(exiler.isPermanent())
						{
							java.util.Map<Integer, java.util.Set<Integer>> controlledBy = state.getTracker(ControlledBy.class).getValue(state);
							if(controlledBy.containsKey(exilerID))
								for(Integer ID: controlledBy.get(exilerID))
									ret.add(state.get(ID));
						}
					}
				}

				return ret;
			}
		}

		/**
		 * Track what was controlled by who. The value is a map from the ID of
		 * the {@link GameObject} being controlled to a {@link java.util.Set} of
		 * IDs of {@link Player} who controlled it.
		 */
		public static final class ControlledBy extends Tracker<java.util.Map<Integer, java.util.Set<Integer>>>
		{
			private java.util.Map<Integer, java.util.Set<Integer>> controlledBy = new java.util.HashMap<Integer, java.util.Set<Integer>>();
			private java.util.Map<Integer, java.util.Set<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.controlledBy);

			@Override
			protected Tracker<java.util.Map<Integer, java.util.Set<Integer>>> clone()
			{
				ControlledBy ret = (ControlledBy)(super.clone());
				ret.controlledBy = new java.util.HashMap<Integer, java.util.Set<Integer>>();
				for(java.util.Map.Entry<Integer, java.util.Set<Integer>> e: this.controlledBy.entrySet())
					ret.controlledBy.put(e.getKey(), new java.util.HashSet<Integer>(e.getValue()));
				ret.unmodifiable = java.util.Collections.unmodifiableMap(this.controlledBy);
				return ret;
			}

			@Override
			protected java.util.Map<Integer, java.util.Set<Integer>> getValueInternal()
			{
				return this.unmodifiable;
			}

			@Override
			protected void reset()
			{
				// Do nothing on purpose as this Tracker is supposed to track
				// for the rest of the game
			}

			@Override
			protected boolean match(GameState state, Event event)
			{
				return (EventType.CHANGE_CONTROL == event.type);
			}

			@Override
			protected void update(GameState state, Event event)
			{
				Set objects = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null);
				if(objects.isEmpty())
					return;

				Set players = event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null);
				if(players.isEmpty())
					return;

				int objectID = objects.getOne(GameObject.class).ID;

				java.util.Set<Integer> controlledBy;
				if(this.controlledBy.containsKey(objectID))
					controlledBy = this.controlledBy.get(objectID);
				else
				{
					controlledBy = new java.util.HashSet<Integer>();
					this.controlledBy.put(objectID, controlledBy);
				}

				controlledBy.add(players.getOne(Player.class).ID);
			}
		}

		public static final class ControllersLook extends StaticAbility
		{
			public ControllersLook(GameState state)
			{
				super(state, "Any player who has controlled the permanent that exiled this card may look at this card in the exile zone.");

				ContinuousEffect.Part look = new ContinuousEffect.Part(ContinuousEffectType.LOOK);
				look.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
				look.parameters.put(ContinuousEffectType.Parameter.PLAYER, ControlledThePermanentThatExiledThis.instance());
				this.addEffectPart(look);

				this.canApply = Intersect.instance(This.instance(), InZone.instance(ExileZone.instance()));
			}
		}

		/**
		 * Track what exiled what. The value is a map from the ID of the exiled
		 * {@link GameObject} to the ID of the {@link GameObject} (or 0 for the
		 * {@link Game}) that exiled it.
		 */
		public static final class ExiledBy extends Tracker<java.util.Map<Integer, Integer>>
		{
			private java.util.Map<Integer, Integer> exiled = new java.util.HashMap<Integer, Integer>();
			private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.exiled);

			@Override
			protected Tracker<java.util.Map<Integer, Integer>> clone()
			{
				ExiledBy ret = (ExiledBy)(super.clone());
				ret.exiled = new java.util.HashMap<Integer, Integer>(this.exiled);
				ret.unmodifiable = java.util.Collections.unmodifiableMap(this.exiled);
				return ret;
			}

			@Override
			protected java.util.Map<Integer, Integer> getValueInternal()
			{
				return this.unmodifiable;
			}

			@Override
			protected void reset()
			{
				// Do nothing on purpose as this Tracker is supposed to track
				// for the rest of the game
			}

			@Override
			protected boolean match(GameState state, Event event)
			{
				if(EventType.MOVE_BATCH != event.type)
					return false;

				int exileZoneID = state.exileZone().ID;
				for(ZoneChange z: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
					if(z.destinationZoneID == exileZoneID)
						return true;

				return false;
			}

			@Override
			protected void update(GameState state, Event event)
			{
				int exileZoneID = state.exileZone().ID;
				for(ZoneChange z: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
				{
					if(z.destinationZoneID == exileZoneID)
					{
						Identified identified = state.get(z.causeID);
						if(identified.isActivatedAbility() || identified.isTriggeredAbility())
							this.exiled.put(z.newObjectID, ((NonStaticAbility)identified).getSource(state).ID);
						else
							this.exiled.put(z.newObjectID, z.causeID);
					}
				}
			}
		}

		public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Hideaway", "Exile one of them face down", false);

		public Exile(GameState state, Class<? extends Linkable> link)
		{
			super(state, "When this permanent enters the battlefield, look at the top four cards of your library. Exile one of them face down and put the rest on the bottom of your library in any order. The exiled card gains 'Any player who has controlled the permanent that exiled this card may look at this card in the exile zone.'");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());

			EventFactory look = new EventFactory(EventType.LOOK, "Look at the top four cards of your library.");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(4, yourLibrary));
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(look);

			SetGenerator cardsLookedAt = EffectResult.instance(look);

			EventFactory exile = new EventFactory(EventType.MOVE_CHOICE, "Exile one of them face down");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.OBJECT, cardsLookedAt);
			exile.parameters.put(EventType.Parameter.CHOICE, Identity.instance(REASON));
			exile.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
			exile.parameters.put(EventType.Parameter.HIDDEN, NonEmpty.instance());
			exile.setLink(this);
			this.addEffect(exile);

			SetGenerator exiledCard = NewObjectOf.instance(EffectResult.instance(exile));

			EventFactory putOnBottom = new EventFactory(EventType.MOVE_OBJECTS, "and put the rest on the bottom of your library in any order.");
			putOnBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOnBottom.parameters.put(EventType.Parameter.TO, yourLibrary);
			putOnBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			putOnBottom.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(cardsLookedAt, exiledCard));
			this.addEffect(putOnBottom);

			String effectName = "The exiled card gains 'Any player who has controlled the permanent that exiled this card may look at this card in the exile zone.'";
			this.addEffect(createFloatingEffect(Empty.instance(), effectName, addAbilityToObject(exiledCard, ControllersLook.class)));

			state.ensureTracker(new ControlledBy());
			state.ensureTracker(new ExiledBy());
			this.getLinkManager().addLinkClass(link);
		}
	}
}
