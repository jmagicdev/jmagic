package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 702.28. Echo
 * 
 * 702.28a Echo is a triggered ability. "Echo [cost]" means
 * "At the beginning of your upkeep, if this permanent came under your control since the beginning of your last upkeep, sacrifice it unless you pay [cost]."
 * 
 * 702.28b Urza block cards with the echo ability were printed without an echo
 * cost. These cards have been given errata in the Oracle card reference; each
 * one now has an echo cost equal to its mana cost.
 */
@Name("Echo")
public final class Echo extends Keyword
{
	protected String manaCostString;

	public Echo(GameState state, String manaCostString)
	{
		super(state, "Echo " + manaCostString);
		this.manaCostString = manaCostString;
	}

	@Override
	public Keyword create(Game game)
	{
		return new Echo(game.physicalState, this.manaCostString);
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new EchoAbility(this.state, this.manaCostString));
		return ret;
	}

	public static final class EchoAbility extends EventTriggeredAbility
	{
		private static final class CameUnderControlSinceLastUpkeep extends SetGenerator
		{
			/**
			 * The algorithm behind this tracker is to have a view map and a map
			 * to add to which can sometimes be the same map. When an upkeep
			 * starts, the view map should continue to be accessible, but any
			 * new information should be stored in a new add map. When an upkeep
			 * ends, the old view map should be discarded and should point to
			 * the add map.
			 */
			public static final class Tracker extends org.rnd.jmagic.engine.Tracker<java.util.Map<Integer, java.util.Set<Integer>>>
			{
				private java.util.Map<Integer, java.util.Set<Integer>> addedSinceLastUpkeep = new java.util.HashMap<Integer, java.util.Set<Integer>>();

				/**
				 * An map of player ID to objects that came under that player's
				 * control since that player's last upkeep
				 */
				private java.util.Map<Integer, java.util.Set<Integer>> controlledSinceLastUpkeep = new java.util.HashMap<Integer, java.util.Set<Integer>>();

				private java.util.Map<Integer, java.util.Set<Integer>> toRemoveAtEndOfUpkeep = new java.util.HashMap<Integer, java.util.Set<Integer>>();

				/**
				 * An map of player ID to an unmodifiable view of objects that
				 * came under that player's control since that player's last
				 * upkeep
				 */
				private java.util.Map<Integer, java.util.Set<Integer>> unmodifiables = new java.util.HashMap<Integer, java.util.Set<Integer>>();

				/**
				 * An unmodifiable map of player ID to an unmodifiable view of
				 * objects that came under that player's control since that
				 * player's last upkeep
				 */
				private java.util.Map<Integer, java.util.Set<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.unmodifiables);

				@Override
				protected Tracker clone()
				{
					Tracker ret = (Tracker)super.clone();

					ret.addedSinceLastUpkeep = new java.util.HashMap<Integer, java.util.Set<Integer>>();
					for(java.util.Map.Entry<Integer, java.util.Set<Integer>> entry: this.addedSinceLastUpkeep.entrySet())
						ret.addedSinceLastUpkeep.put(entry.getKey(), new java.util.HashSet<Integer>(entry.getValue()));

					ret.controlledSinceLastUpkeep = new java.util.HashMap<Integer, java.util.Set<Integer>>();
					for(java.util.Map.Entry<Integer, java.util.Set<Integer>> entry: this.controlledSinceLastUpkeep.entrySet())
						ret.controlledSinceLastUpkeep.put(entry.getKey(), new java.util.HashSet<Integer>(entry.getValue()));

					ret.toRemoveAtEndOfUpkeep = new java.util.HashMap<Integer, java.util.Set<Integer>>();
					for(java.util.Map.Entry<Integer, java.util.Set<Integer>> entry: this.toRemoveAtEndOfUpkeep.entrySet())
						ret.toRemoveAtEndOfUpkeep.put(entry.getKey(), new java.util.HashSet<Integer>(entry.getValue()));

					ret.unmodifiables = new java.util.HashMap<Integer, java.util.Set<Integer>>();
					for(java.util.Map.Entry<Integer, java.util.Set<Integer>> entry: ret.controlledSinceLastUpkeep.entrySet())
						ret.unmodifiables.put(entry.getKey(), java.util.Collections.unmodifiableSet(entry.getValue()));

					ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.unmodifiables);

					return ret;
				}

				@Override
				protected final java.util.Map<Integer, java.util.Set<Integer>> getValueInternal()
				{
					return this.unmodifiable;
				}

				@Override
				protected boolean match(GameState state, Event event)
				{
					if((EventType.BEGIN_STEP == event.type) || (EventType.END_STEP == event.type))
					{
						Step step = event.parameters.get(EventType.Parameter.STEP).evaluate(state, null).getOne(Step.class);
						if(Step.StepType.UPKEEP == step.type)
							return true;
					}
					else if(EventType.CHANGE_CONTROL == event.type)
					{
						if(event.parameters.containsKey(EventType.Parameter.TARGET))
							return true;
					}
					return false;
				}

				@Override
				protected final void reset()
				{
					// Don't clear the map since this has to track information
					// for longer than a turn - it clears itself after every
					// upkeep anyway
				}

				@Override
				protected void update(GameState state, Event event)
				{
					if(EventType.BEGIN_STEP == event.type)
					{
						Step step = event.parameters.get(EventType.Parameter.STEP).evaluate(state, null).getOne(Step.class);
						Player player = step.getOwner(state);

						if(this.addedSinceLastUpkeep.containsKey(player.ID))
							this.toRemoveAtEndOfUpkeep.put(player.ID, this.addedSinceLastUpkeep.get(player.ID));
						this.addedSinceLastUpkeep.put(player.ID, new java.util.HashSet<Integer>());
					}
					else if(EventType.END_STEP == event.type)
					{
						Step step = event.parameters.get(EventType.Parameter.STEP).evaluate(state, null).getOne(Step.class);
						Player player = step.getOwner(state);

						if(this.controlledSinceLastUpkeep.containsKey(player.ID) && this.toRemoveAtEndOfUpkeep.containsKey(player.ID))
							this.controlledSinceLastUpkeep.get(player.ID).removeAll(this.toRemoveAtEndOfUpkeep.get(player.ID));
					}
					// Must be a CHANGE_CONTROL event
					else
					{
						GameObject object = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
						Player player = event.parameters.get(EventType.Parameter.TARGET).evaluate(state, null).getOne(Player.class);

						java.util.Set<Integer> added;
						if(this.addedSinceLastUpkeep.containsKey(player.ID))
							added = this.addedSinceLastUpkeep.get(player.ID);
						else
						{
							added = new java.util.HashSet<Integer>();
							this.addedSinceLastUpkeep.put(player.ID, added);
						}
						added.add(object.ID);

						java.util.Set<Integer> controlled;
						if(this.controlledSinceLastUpkeep.containsKey(player.ID))
							controlled = this.controlledSinceLastUpkeep.get(player.ID);
						else
						{
							controlled = new java.util.HashSet<Integer>();
							this.controlledSinceLastUpkeep.put(player.ID, controlled);
							this.unmodifiables.put(player.ID, java.util.Collections.unmodifiableSet(controlled));
						}
						controlled.add(object.ID);
					}
				}
			}

			public static SetGenerator instance(SetGenerator controller)
			{
				return new CameUnderControlSinceLastUpkeep(controller);
			}

			private SetGenerator controller;

			private CameUnderControlSinceLastUpkeep(SetGenerator controller)
			{
				this.controller = controller;
			}

			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				Set ret = new Set();
				for(Player p: this.controller.evaluate(state, thisObject).getAll(Player.class))
				{
					java.util.Map<Integer, java.util.Set<Integer>> map = state.getTracker(Tracker.class).getValue(state);
					if(!map.containsKey(p.ID))
						continue;
					for(int ID: map.get(p.ID))
						ret.add(state.get(ID));
				}
				return ret;
			}
		}

		private String manaCostString;

		public EchoAbility(GameState state, String manaCostString)
		{
			super(state, "At the beginning of your upkeep, if this permanent came under your control since the beginning of your last upkeep, sacrifice it unless you pay " + manaCostString + ".");
			this.manaCostString = manaCostString;
			this.addPattern(atTheBeginningOfYourUpkeep());

			state.ensureTracker(new CameUnderControlSinceLastUpkeep.Tracker());
			this.interveningIf = Intersect.instance(CameUnderControlSinceLastUpkeep.instance(You.instance()), ABILITY_SOURCE_OF_THIS);

			EventFactory payMana = new EventFactory(EventType.PAY_MANA, "Pay " + manaCostString);
			payMana.parameters.put(EventType.Parameter.CAUSE, This.instance());
			payMana.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool(manaCostString)));
			payMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(unless(You.instance(), sacrificeThis("this permanent"), payMana, "Sacrifice this permanent unless you pay " + manaCostString + "."));
		}

		@Override
		public EchoAbility create(Game game)
		{
			return new EchoAbility(game.physicalState, this.manaCostString);
		}
	}
}
