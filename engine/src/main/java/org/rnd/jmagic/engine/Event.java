package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/**
 * "Anything that happens in a game is an event." (Glossary entry for Event.)
 */
public class Event extends Identified implements Sanitizable
{
	public static class IndexedZone
	{
		public int index;
		public int zoneID;

		public IndexedZone(int index, Zone zone)
		{
			this.index = index;
			this.zoneID = zone.ID;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(!(obj instanceof IndexedZone))
				return false;
			IndexedZone other = (IndexedZone)obj;
			if(this.index != other.index)
				return false;
			if(this.zoneID != other.zoneID)
				return false;
			return true;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + this.index;
			result = prime * result + this.zoneID;
			return result;
		}
	}

	/**
	 * Whether all the choices required for this event were made (for example,
	 * when asked to discard four cards, were there actually four cards to
	 * choose?). This is to keep event types from needing to duplicate number
	 * computation in perform after having done it in makeChoices. Defaults to
	 * true.
	 */
	public boolean allChoicesMade;

	/**
	 * An effect that modifies how a permanent enters the battlefield may cause
	 * other objects to change zones. Such an effect can't cause the permanent
	 * itself to not enter the battlefield.
	 * 
	 * Tells this Event that it can't move the specified objects in accordance
	 * with this rule.
	 */
	public java.util.Collection<Integer> cantMoveIDs;

	/**
	 * Keys are events this event has performed, values are return values from
	 * {@link Event#perform()}
	 */
	public java.util.Map<Event, Boolean> children;
	/**
	 * Choices made for this event; for example, what to sacrifice for Innocent
	 * Blood, or what to put onto the battlefield from Hunted Wumpus. Null when
	 * choices haven't yet been made for this event.
	 * 
	 * Keys are player IDs, values are the choices those players made.
	 */
	private java.util.Map<Integer, Identity> choices;

	/**
	 * Damage to be dealt during this event.
	 */
	private DamageAssignment.Batch damage;

	/**
	 * If true, this event's zone changes will get their new objects after it
	 * performs, even if this event is not top level. Used by replacement
	 * effects that add movement effects to their effect lists, since those
	 * effects are never top level.
	 */
	public boolean forceZoneChanges;

	/** Whether this event is part of a cost. */
	public boolean isCost;

	public boolean isEffect;

	/**
	 * Objects needing new timestamps as a result of this event (only applies to
	 * top-level events)
	 */
	private java.util.Set<GameObject> needNewTimestamps;

	/**
	 * Cards which were moved by this event or its children (only applies to
	 * top-level events) and that should be ordered by their owners
	 */
	private java.util.Map<IndexedZone, java.util.Set<Integer>> objectsToOrderByChoice;

	/**
	 * Cards which were moved by this event or its children (only applies to
	 * top-level events) and that should be ordered randomly
	 */
	private java.util.Map<IndexedZone, java.util.Set<Integer>> objectsToOrderRandomly;

	/**
	 * The parameters of this event.
	 */
	public java.util.Map<EventType.Parameter, SetGenerator> parameters;

	/** The parameters of this event as they were most recently evaluated. */
	public java.util.Map<EventType.Parameter, Identity> parametersNow;

	/** The event that performed this event. */
	public Event parent;

	/**
	 * The ID of an event to pass this event's result to after this event is
	 * performed.
	 */
	public int passResultID;

	/**
	 * Whether or not to preserve this event in the GameState it's in. This
	 * defaults to false and should be set to true if something might refer to
	 * it.
	 */
	public boolean preserve;

	/** IDs of replacement effects that have replaced this event */
	public final java.util.Collection<ReplacementEffect> replacedBy;

	private Identity result;

	/**
	 * IDs of libraries to shuffle after performing zone changes from this
	 * event.
	 */
	private java.util.Set<Integer> shuffles;

	private int sourceID;

	/**
	 * ID of the linkable object to store the results in. (THIS IS CODED IN SUCH
	 * A WAY THAT IT CAN ONLY BE AN EVENT TRIGGERED ABILITY'S ID. IF WE EVER
	 * NEED AN ACTIVATED ABILITY HERE, CODE NEEDS TO CHANGE.)
	 */
	private int storeIn;

	private boolean topLevel;

	public final EventType type;

	/**
	 * If this event is to be performed as a cost and uses a ValueOfX generator,
	 * this must be true so that the CSOAA event knows to ask the user for a
	 * value for X.
	 */
	public boolean usesX;

	/**
	 * Zone changes to perform during this event.
	 */
	private java.util.List<ZoneChange> zoneChanges;

	/**
	 * @param state The game state in which this event is to be performed.
	 * @param name The text of this event.
	 * @param type What kind of event this is.
	 * 
	 * Constructs an event with an empty parameter map.
	 */
	public Event(GameState state, String name, EventType type)
	{
		super(state);

		this.setName(name);

		this.allChoicesMade = true;
		this.cantMoveIDs = java.util.Collections.emptyList();
		this.children = new java.util.HashMap<Event, Boolean>();
		this.choices = null;
		this.damage = new DamageAssignment.Batch();
		this.forceZoneChanges = false;
		this.isCost = false;
		this.isEffect = true;
		this.needNewTimestamps = new java.util.HashSet<GameObject>();
		this.objectsToOrderByChoice = new java.util.HashMap<IndexedZone, java.util.Set<Integer>>();
		this.objectsToOrderRandomly = new java.util.HashMap<IndexedZone, java.util.Set<Integer>>();
		this.passResultID = -1;
		this.parameters = new java.util.HashMap<EventType.Parameter, SetGenerator>();
		this.parametersNow = new java.util.HashMap<EventType.Parameter, Identity>();
		this.parent = null;
		this.preserve = false;
		this.replacedBy = new java.util.LinkedList<ReplacementEffect>();
		this.result = null;
		this.shuffles = new java.util.HashSet<Integer>();
		this.sourceID = -1;
		this.storeIn = -1;
		this.topLevel = false;
		this.type = type;
		this.zoneChanges = new java.util.LinkedList<ZoneChange>();
	}

	/**
	 * Tells this event to add one damage assignment to the damage it will deal
	 * when performed.
	 * 
	 * @param source The source of the damage.
	 * @param taker What is taking the damage.
	 * @param unpreventable Whether "the damage can't be prevented".
	 */
	public final void addDamage(GameObject source, Identified taker, boolean unpreventable)
	{
		DamageAssignment a = new DamageAssignment(source, taker);
		a.isUnpreventable = unpreventable;
		this.damage.add(a);
	}

	/**
	 * Tells this event to add some damage to what it will deal when performed.
	 * 
	 * @param assignments The damage to deal.
	 */
	public final void addDamage(java.util.Collection<DamageAssignment> assignments)
	{
		this.damage.addAll(assignments);
	}

	public final void addShuffle(int libraryID)
	{
		if(!this.topLevel)
			this.getTopLevelParent().addShuffle(libraryID);
		else
			this.shuffles.add(libraryID);
	}

	/**
	 * Tells this event that an object needs a new timestamp after this event
	 * finishes performing.
	 */
	public final void addToNeedsNewTimestamps(GameObject object)
	{
		if(!this.topLevel)
			this.getTopLevelParent().addToNeedsNewTimestamps(object);
		else
			this.needNewTimestamps.add(object);
	}

	/**
	 * Tells this event that an object was moved while it was being performed.
	 * 
	 * @param object The object that was moved.
	 * @param index The index at which the object was inserted.
	 * @param zone The zone it was moved to.
	 */
	public final void addToObjectsToOrderByChoice(GameObject object, int index, Zone zone)
	{
		// only top-level events want this information
		if(!this.topLevel)
		{
			this.getTopLevelParent().addToObjectsToOrderByChoice(object, index, zone);
			return;
		}

		IndexedZone indexedZone = new IndexedZone(index, zone);
		if(!this.objectsToOrderByChoice.containsKey(indexedZone))
			this.objectsToOrderByChoice.put(indexedZone, new java.util.HashSet<Integer>());

		// add the object to the zone
		this.objectsToOrderByChoice.get(indexedZone).add(object.ID);
	}

	/**
	 * Tells this event that an object was moved while it was being performed.
	 * 
	 * @param object The object that was moved.
	 * @param index The index at which the object was inserted.
	 * @param zone The zone it was moved to.
	 */
	public final void addToObjectsToOrderRandomly(GameObject object, int index, Zone zone)
	{
		// only top-level events want this information
		if(!this.topLevel)
		{
			this.getTopLevelParent().addToObjectsToOrderRandomly(object, index, zone);
			return;
		}

		IndexedZone indexedZone = new IndexedZone(index, zone);
		if(!this.objectsToOrderRandomly.containsKey(indexedZone))
			this.objectsToOrderRandomly.put(indexedZone, new java.util.HashSet<Integer>());

		// add the object to the zone
		this.objectsToOrderRandomly.get(indexedZone).add(object.ID);
	}

	public final void addZoneChange(ZoneChange zoneChange)
	{
		this.zoneChanges.add(zoneChange);
	}

	private void afterPerformChecks(GameState previousGameState)
	{
		// If this was a top level event, do the following:
		// 1. perform replacement effects on damage
		// 2. perform events resulting from those effects
		// (repeat until there are no more replacement effects)
		// 3. deal damage
		// 4. order any cards put into ordered zones by this event
		// 5. refresh the game state
		// 6. check for delayed one-shot effects that should perform now
		// 7. search for stuff that should be removed from combat and remove it
		// 8. check for triggered abilities.
		// We only check for triggered abilities after top level events because
		// the game state needs to have actually changed. same with delayed
		// one-shots.
		if(this.topLevel)
		{
			this.ensureValidZoneChanges();

			// Perform any events associated with those zone-changes
			for(ZoneChange change: this.zoneChanges)
				for(EventFactory factory: change.events)
					factory.createEvent(this.game, this.getSource()).perform(this, false);

			if(!this.damage.isEmpty())
				while(replaceDamage())
				{
					// this loops until there are no more damage replacements to
					// apply
				}

			this.ensureValidZoneChanges();

			java.util.Collection<ZoneChangeReplacementEffect> zcrEffectsUsed = new java.util.LinkedList<ZoneChangeReplacementEffect>();
			// This loops until there are no more zone-change replacements to
			// apply
			while(true)
			{
				ZoneChangeReplacementEffect newEffect = replaceZoneChanges();
				if(newEffect == null)
					break;
				zcrEffectsUsed.add(newEffect);

				// You need to refresh here for when a replaced zone change does
				// something silly like make new abilities (Hi Clone)
				this.game.refreshActualState();
			}

			// deal some damage
			if(!this.damage.isEmpty())
			{
				Event damageEvent = new Event(this.game.physicalState, "Damage!", EventType.DEAL_DAMAGE_BATCHES);
				damageEvent.parameters.put(EventType.Parameter.TARGET, Identity.fromCollection(this.damage));
				damageEvent.perform(this, false);
			}

			// Take care of zone changes that shouldn't happen because they'll
			// move a permanent that is trying to enter the battlefield
			if(!this.zoneChanges.isEmpty())
			{
				{
					java.util.Iterator<ZoneChange> z = this.zoneChanges.iterator();
					while(z.hasNext())
						if(this.cantMoveIDs.contains(z.next().oldObjectID))
							z.remove();
				}

				// Move any GameObjects that need to be moved
				if(!this.zoneChanges.isEmpty())
				{
					this.ensureValidZoneChanges();

					java.util.Map<EventType.Parameter, Set> zoneChangeParameters = new java.util.HashMap<EventType.Parameter, Set>();
					zoneChangeParameters.put(EventType.Parameter.TARGET, Set.fromCollection(this.zoneChanges));
					EventType.createEvent(this.game, "Movements", EventType.MOVE_BATCH, zoneChangeParameters).perform(this, false);

					for(ZoneChangeReplacementEffect effect: zcrEffectsUsed)
						for(EventFactory a: effect.afterEffects)
							a.createEvent(this.game, (GameObject)effect.getSourceObject(this.state)).perform(this, true);
				}
			}

			if(this.type != EventType.GAME_OVER && (this.game.physicalState.numPlayers() < 2))
				throw new Game.GameOverException();

			orderMovedCards();
			for(int i: this.shuffles)
				shuffle(i);
			orderTimestamps();

			// refresh the actual state before checking for triggered abilities
			this.game.refreshActualState();

			this.game.actualState.fireDelayedOneShots();

			Event newThis = this.game.actualState.get(this.ID);
			newThis.removeFromCombat(previousGameState);
			this.checkForTriggeredAbilities(previousGameState);

			// Dispose of the previous game state
			previousGameState.clear();
			previousGameState = null;
		}
		else
		// this isn't top level
		{
			if(this.forceZoneChanges)
				this.ensureValidZoneChanges();

			if(this.parent != null)
			{
				// If this isn't top level, pass its damage up, making sure it
				// doesn't get re-replaced.
				for(DamageAssignment assignment: this.damage)
					assignment.replacedBy.addAll(this.replacedBy);
				this.parent.damage.addAll(this.damage);

				this.parent.zoneChanges.addAll(this.zoneChanges);
			}
		}
	}

	private final void allSuccessfulDescendants(java.util.Collection<Event> descendants)
	{
		for(java.util.Map.Entry<Event, Boolean> child: this.children.entrySet())
		{
			Event childEvent = child.getKey();
			if(child.getValue())
				descendants.add(childEvent);
			if(!childEvent.topLevel)
				childEvent.allSuccessfulDescendants(descendants);
		}
	}

	/**
	 * @param event The parent event this event should use.
	 * @return Whether this event could possibly be performed.
	 */
	public final boolean attempt(Event event)
	{
		Event backupParent = this.parent;
		this.parent = event;

		if(!this.validate() || this.isProhibited(this.game.actualState))
		{
			this.parent = backupParent;
			return false;
		}

		boolean ret = this.type.attempt(this.game, this, this.parametersNowAsSets());

		this.parent = backupParent;
		return ret;
	}

	private void checkForTriggeredAbilities(GameState previousGameState)
	{
		java.util.Collection<Event> eventsRecentlyPerformed = new java.util.LinkedList<Event>();
		this.allSuccessfulDescendants(eventsRecentlyPerformed);
		eventsRecentlyPerformed.add(this);

		// collect all state- and event-triggered abilities in this game state
		java.util.List<StateTriggeredAbility> stateTriggers = new java.util.LinkedList<StateTriggeredAbility>();
		java.util.List<EventTriggeredAbility> eventTriggers = new java.util.LinkedList<EventTriggeredAbility>();
		for(GameObject o: this.game.actualState.getAllObjects())
			if(o.zoneID != -1)
				for(NonStaticAbility a: o.getNonStaticAbilities())
				{
					// TODO : instanceof HATE
					if(a instanceof StateTriggeredAbility)
						stateTriggers.add((StateTriggeredAbility)a);
					else if(a instanceof EventTriggeredAbility)
						eventTriggers.add((EventTriggeredAbility)a);
				}

		// collect delayed triggers
		for(DelayedTrigger a: this.game.actualState.delayedTriggers)
			eventTriggers.add(a);

		// collect lookback triggers
		java.util.List<EventTriggeredAbility> lookbackTriggers = new java.util.LinkedList<EventTriggeredAbility>();
		for(GameObject o: previousGameState.getAllObjects())
			if(o.zoneID != -1)
				for(NonStaticAbility a: o.getNonStaticAbilities())
					if(a instanceof EventTriggeredAbility)
						lookbackTriggers.add((EventTriggeredAbility)a);

		// collected delayed lookback triggers
		for(DelayedTrigger a: previousGameState.delayedTriggers)
			lookbackTriggers.add(a);

		for(StateTriggeredAbility trigger: stateTriggers)
			if(trigger.triggersNow())
				trigger.trigger();

		for(Event event: eventsRecentlyPerformed)
		{
			for(EventTriggeredAbility trigger: eventTriggers)
				if(trigger.triggerOn(event, this.game.actualState, false))
					this.preserve = true;

			for(EventTriggeredAbility trigger: lookbackTriggers)
				if(trigger.triggerOn(event, previousGameState, true))
					this.preserve = true;
		}

		TriggeredAbility trigger;
		do
		{
			trigger = null;
			outerFor: for(java.util.Collection<TriggeredAbility> collection: this.game.physicalState.waitingTriggers.values())
				for(TriggeredAbility ability: collection)
					if(ability.isManaAbility())
					{
						trigger = ability;
						collection.remove(trigger);
						break outerFor;
					}

			if(trigger != null)
			{
				// this whole loop is just for triggered mana abilities, don't
				// panic. (unless there's a modal triggered mana ability, in
				// which case you should panic, but not because of this code...)
				for(int i = 0; i < trigger.getModes().size(); i++)
					trigger.getSelectedModeNumbers().add(i + 1);
				trigger.clone(this.game.actualState);

				// Add it to the currently resolving mana abilities list so that
				// it correctly ghosts after resolving.
				this.game.physicalState.currentlyResolvingManaAbilities.add(trigger);
				trigger.resolve();
			}
			else
				break;
		}
		while(true);
	}

	private java.util.List<Event> checkReplacements()
	{
		java.util.List<EventReplacementEffect> effects = new java.util.LinkedList<EventReplacementEffect>();
		for(EventReplacementEffect effect: this.game.actualState.eventReplacementEffects)
			if(effect.appliesTo(this))
				effects.add(effect);

		if(effects.size() == 0)
			return null;
		if(effects.size() == 1)
			for(EventReplacementEffect effect: effects)
				return effect.apply(this);

		// competing replacement effects:
		if(null == this.type.affects())
			return new java.util.LinkedList<Event>();
		Set affected = this.parametersNow.get(this.type.affects()).evaluate(this.game, null);
		java.util.Set<Player> affectedPlayers = affected.getAll(Player.class);
		for(Controllable c: affected.getAll(Controllable.class))
			affectedPlayers.add(c.getController(this.game.actualState));
		// TODO : solve picking order issue -- it's APNAP
		if(affectedPlayers.size() > 1)
			throw new IllegalStateException("Multiple affected players on one replaceable event...");

		Player affectedPlayer = null;
		for(Player player: affectedPlayers)
			affectedPlayer = player;

		PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(1, 1, PlayerInterface.ChoiceType.REPLACEMENT_EFFECT, PlayerInterface.ChooseReason.REPLACE_EVENT);
		chooseParameters.thisID = this.ID;
		java.util.List<EventReplacementEffect> choice = affectedPlayer.sanitizeAndChoose(this.game.actualState, effects, chooseParameters);
		return choice.get(0).apply(this);
	}

	private void clearParameters()
	{
		// It is actually possible for this method to be called twice on the
		// same event. Consider an Event "X". If one of X's children is top
		// level (which can happen, for example, if one of its children is a
		// card drawing event), this method will be called on that child when it
		// finishes performing, and then again on that child recursively when
		// it's called on X. The recursion being inside this if-statement is an
		// optimization; parameters can only be null if this method was already
		// called, which would mean it has already recursed as well.
		if(this.parameters != null)
		{
			// clear the map in case anyone else is inadvertently referencing it
			this.parameters.clear();
			// null it to hint the gc
			this.parameters = null;

			for(Event child: this.children.keySet())
				child.clearParameters();
		}
	}

	/**
	 * Java-copies this event.
	 */
	@Override
	public Event clone(GameState state)
	{
		Event ret = (Event)super.clone(state);

		if(this.parameters == null)
			ret.parameters = null;
		else
		{
			ret.parameters = new java.util.HashMap<EventType.Parameter, SetGenerator>();
			for(EventType.Parameter parameterName: this.parameters.keySet())
				ret.parameters.put(parameterName, this.parameters.get(parameterName));
		}

		if(this.choices != null)
			ret.choices.putAll(this.choices);

		// result copied automatically, shallow copy is fine

		// parent, children, objects moved, and needNewTimestamps don't need to
		// be preserved between backups
		ret.children = new java.util.HashMap<Event, Boolean>();
		ret.parent = null;
		ret.shuffles = new java.util.HashSet<Integer>(this.shuffles);
		ret.needNewTimestamps = new java.util.HashSet<GameObject>();
		ret.objectsToOrderByChoice = new java.util.HashMap<IndexedZone, java.util.Set<Integer>>();
		ret.objectsToOrderRandomly = new java.util.HashMap<IndexedZone, java.util.Set<Integer>>();
		return ret;
	}

	public Event create(Game game)
	{
		Event ret = new Event(game.physicalState, this.getName(), this.type);
		ret.parameters.putAll(this.parameters);
		if(this.choices != null)
			ret.choices.putAll(this.choices);

		return ret;
	}

	private void ensureValidZoneChanges()
	{
		if(this.zoneChanges.isEmpty())
			return;

		// Create new GameObject to be moved
		for(ZoneChange change: this.zoneChanges)
		{
			if(change.newObjectID == -1)
			{
				Zone destination = this.game.actualState.get(change.destinationZoneID);
				GameObject newObject = this.game.physicalState.<GameObject>get(change.oldObjectID).createToMove(destination);
				change.newObjectID = newObject.ID;

				// 121.6. If a spell or ability refers to a counter being
				// "placed" on a permanent, it means putting a counter on that
				// permanent while it's on the battlefield, or that permanent
				// entering the battlefield with a counter on it as the result
				// of a replacement effect (see rule 614.1c).
				// This line ensures this rule works; otherwise at the time the
				// PUT_ONE_COUNTER event was happening the object wouldn't have
				// a controllerID to check yet.
				newObject.controllerID = change.controllerID;
			}
		}
		this.game.refreshActualState();
	}

	/**
	 * @return What <code>madeChoices</code> chose for this event. If no choices
	 * have been made yet, returns empty.
	 */
	public final Set getChoices(Player madeChoices)
	{
		if(this.choices == null || !this.choices.containsKey(madeChoices.ID))
			return Empty.set;
		return this.choices.get(madeChoices.ID).evaluate(this.game, null);
	}

	/**
	 * @return An unmodifiable view of damage this event would deal or has
	 * dealt.
	 */
	public final java.util.Collection<DamageAssignment> getDamage()
	{
		return java.util.Collections.unmodifiableCollection(this.damage);
	}

	public final java.util.Map<IndexedZone, java.util.Set<GameObject>> getObjectsMoved(GameState state)
	{
		java.util.Map<IndexedZone, java.util.Set<GameObject>> ret = new java.util.HashMap<IndexedZone, java.util.Set<GameObject>>();
		for(java.util.Map.Entry<IndexedZone, java.util.Set<Integer>> entry: this.objectsToOrderByChoice.entrySet())
		{
			java.util.Set<GameObject> objects = new java.util.HashSet<GameObject>();
			for(Integer i: entry.getValue())
				objects.add(state.<GameObject>get(i));
			ret.put(entry.getKey(), objects);
		}
		return ret;
	}

	/** Evaluates this event's result in its game and returns it. */
	public final Set getResult()
	{
		if(this.result == null)
			return Empty.set;
		return this.result.evaluate(this.game, null);
	}

	/** Evaluates this event's result in the given game state and returns it. */
	public final Set getResult(GameState state)
	{
		if(this.result == null)
			return Empty.set;
		return this.result.evaluate(state, null);
	}

	/** Returns this event's result generator. */
	public final Identity getResultGenerator()
	{
		return this.result;
	}

	public final GameObject getSource()
	{
		if(this.sourceID == -1)
			return null;
		return this.game.actualState.get(this.sourceID);
	}

	/**
	 * @return Before the event is performed: this. Once the event has started
	 * performing: The event "all the way up the chain" that was ultimately
	 * responsible for this event's creation and performing.
	 */
	public final Event getTopLevelParent()
	{
		if(this.topLevel)
			return this;
		return this.parent.getTopLevelParent();
	}

	public java.util.List<ZoneChange> getZoneChanges()
	{
		return new java.util.LinkedList<ZoneChange>(this.zoneChanges);
	}

	/**
	 * @param state Which GameState to check prohibitions within
	 * @return Whether or not this event is prohibited based on the list of
	 * prohibitions stored in the state argument
	 */
	public boolean isProhibited(GameState state)
	{
		GameObject source = this.getSource();
		for(EventPattern p: state.eventProhibitions)
			if(p.match(this, source, state))
				return true;
		return false;
	}

	public final boolean isTopLevel()
	{
		return this.topLevel;
	}

	public boolean makeChoices(Event parent)
	{
		Event parentBackup = this.parent;
		this.parent = parent;

		if(this.parametersNow.isEmpty())
			if(!this.validate())
			{
				this.parent = parentBackup;
				return false;
			}

		this.type.makeChoices(this.game, this, this.parametersNowAsSets());

		this.parent = parentBackup;
		return this.allChoicesMade;
	}

	/**
	 * This orders all cards put into ordered zones by this event
	 */
	private void orderMovedCards()
	{
		for(java.util.Map.Entry<IndexedZone, java.util.Set<Integer>> objectsMovedEntry: this.objectsToOrderByChoice.entrySet())
		{
			IndexedZone indexedZone = objectsMovedEntry.getKey();
			Zone zone = this.game.physicalState.get(indexedZone.zoneID);

			// only give the player choices for cards that are still in the
			// indexed zone
			java.util.Collection<GameObject> choices = new java.util.HashSet<GameObject>();
			for(Integer i: objectsMovedEntry.getValue())
			{
				GameObject object = this.game.actualState.get(i);
				if(zone.objects.contains(object))
					choices.add(object);
			}

			Player owner = zone.getOwner(this.game.actualState);

			// 401.4. If an effect puts two or more cards on the top or bottom
			// of a library at the same time, the owner of those cards may
			// arrange them in any order. That library's owner doesn't reveal
			// the order in which the cards go into his or her library.
			if(zone.isLibrary() && ((1 == indexedZone.index) || (-1 == indexedZone.index)))
			{
				PlayerInterface.ChooseReason description = (1 == indexedZone.index) ? PlayerInterface.ChooseReason.ORDER_LIBRARY_TOP : PlayerInterface.ChooseReason.ORDER_LIBRARY_BOTTOM;
				if(2 <= choices.size())
					for(GameObject object: owner.sanitizeAndChoose(this.game.actualState, choices.size(), choices, PlayerInterface.ChoiceType.MOVEMENT_LIBRARY, description))
						if(zone.objects.remove(object))
							zone.addAtPosition(object, indexedZone.index);
			}
			// 404.3. If an effect or rule puts two or more cards into the same
			// graveyard at the same time, the owner of those cards may arrange
			// them in any order.
			else if(zone.isGraveyard())
			{
				if(2 <= choices.size())
					for(GameObject object: owner.sanitizeAndChoose(this.game.actualState, choices.size(), choices, PlayerInterface.ChoiceType.MOVEMENT_GRAVEYARD, PlayerInterface.ChooseReason.ORDER_GRAVEYARD))
						if(zone.objects.remove(object))
							zone.addToTop(object);
			}
			// 405.3. If an effect puts two or more objects on the stack at the
			// same time, those controlled by the active player are put on
			// lowest, followed by each other player's objects in APNAP order
			// (see rule 101.4). If a player controls more than one of these
			// objects, that player chooses their relative order on the stack.
			else if(this.state.stack().equals(zone))
			{
				if(2 <= choices.size())
				{
					// Break the objects up by controller
					java.util.Map<Player, java.util.Set<GameObject>> controllers = new java.util.HashMap<Player, java.util.Set<GameObject>>();
					for(GameObject o: choices)
					{
						java.util.Set<GameObject> controlledBy;
						Player controller = o.getController(this.game.actualState);
						if(controllers.containsKey(controller))
							controlledBy = controllers.get(controller);
						else
						{
							controlledBy = new java.util.HashSet<GameObject>();
							controllers.put(controller, controlledBy);
						}
						controlledBy.add(o);
					}

					for(java.util.Map.Entry<Player, java.util.Set<GameObject>> controllersEntry: controllers.entrySet())
					{
						choices = controllersEntry.getValue();
						if(1 < choices.size())
							choices = controllersEntry.getKey().sanitizeAndChoose(this.game.actualState, choices.size(), choices, PlayerInterface.ChoiceType.MOVEMENT_STACK, PlayerInterface.ChooseReason.ORDER_STACK);

						for(GameObject object: choices)
							if(zone.objects.remove(object))
								zone.addToTop(object);
					}
				}
			}
		}
		for(java.util.Map.Entry<IndexedZone, java.util.Set<Integer>> objectsMovedEntry: this.objectsToOrderRandomly.entrySet())
		{
			IndexedZone indexedZone = objectsMovedEntry.getKey();
			Zone zone = this.game.physicalState.get(indexedZone.zoneID);

			java.util.List<GameObject> choices = new java.util.LinkedList<GameObject>();
			for(Integer i: objectsMovedEntry.getValue())
			{
				GameObject object = this.game.actualState.get(i);
				if(zone.objects.contains(object))
					choices.add(object);
			}

			java.util.Collections.shuffle(choices);
			for(GameObject object: choices)
				if(zone.objects.remove(object))
					zone.addAtPosition(object, indexedZone.index);
		}
	}

	/**
	 * Asks the player to choose timestamps for objects that would receive them
	 * simultaneously. A sane PlayerInterface implementation probably will not
	 * actually ask the player to do anything with this.
	 */
	private void orderTimestamps()
	{
		// If the game hasn't started yet, there's no active player to determine
		// timestamps.
		if(this.game.actualState.currentTurn() == null)
		{
			for(GameObject o: this.needNewTimestamps)
				o.getPhysical().setNewTimestamp();
			return;
		}

		Player activePlayer = this.game.actualState.currentTurn().getOwner(this.game.actualState);
		java.util.Set<GameObject> needNewTimestamps = new java.util.HashSet<GameObject>(this.needNewTimestamps);
		java.util.Iterator<GameObject> iter = needNewTimestamps.iterator();
		while(iter.hasNext())
		{
			GameObject o = iter.next();
			boolean visibleToAll = true;
			for(Player p: this.game.actualState.players)
				if(!p.outOfGame && !o.getActual().isVisibleTo(p))
				{
					visibleToAll = false;
					break;
				}
			if(!visibleToAll)
			{
				o.getPhysical().setNewTimestamp();
				iter.remove();
			}
		}

		// Don't present a choice when there isn't one.
		if(needNewTimestamps.size() <= 1)
		{
			for(GameObject o: needNewTimestamps)
				o.getPhysical().setNewTimestamp();
			return;
		}

		// 613.6e If two or more objects would receive a timestamp
		// simultaneously, such as by entering a zone simultaneously or becoming
		// attached simultaneously, the active player determines their timestamp
		// order at that time.
		java.util.List<GameObject> ordered = activePlayer.sanitizeAndChoose(this.game.actualState, needNewTimestamps.size(), needNewTimestamps, PlayerInterface.ChoiceType.TIMESTAMPS, PlayerInterface.ChooseReason.TIMESTAMP);
		for(GameObject o: ordered)
			o.getPhysical().setNewTimestamp();
	}

	private java.util.Map<EventType.Parameter, Set> parametersNowAsSets()
	{
		java.util.Map<EventType.Parameter, Set> ret = new java.util.HashMap<EventType.Parameter, Set>();
		for(java.util.Map.Entry<EventType.Parameter, Identity> parameter: this.parametersNow.entrySet())
			ret.put(parameter.getKey(), parameter.getValue().evaluate(this.game, null));
		return ret;
	}

	/**
	 * Performs this event.
	 * 
	 * TODO : GOD DAMN THIS METHOD IS HUGE. Break it up a little. Kamikaze: I
	 * started this, but it should be broken up further.
	 * 
	 * @return Whether this event can be used to pay a cost.
	 */
	private boolean perform()
	{
		// if this event is a top level cost or effect of a card, get a copy of
		// it as it exists on the physical card
		Event physicalEvent = this.game.physicalState.get(this.ID);

		boolean eventValid = this.validate();

		// 701.15c If an effect would cause a player to shuffle one or more
		// specific objects into a library, ...
		if(this.type == EventType.SHUFFLE_INTO_LIBRARY &&
		// ... but none of those objects are in the zone they're expected to be
		// in, ...
		// (we will have removed those objects since they will have been
		// ghosted; this means that the event must be invalid -- at least one
		// ghost was removed -- and that there are no objects left to shuffle)
		!eventValid && this.parametersNow.get(EventType.Parameter.OBJECT).evaluate(this.game, this.getSource()).getAll(GameObject.class).isEmpty())
			// ... that library is not shuffled.
			return false;

		if(this.isProhibited(this.game.actualState))
		{
			this.setResult(Empty.set);
			return false;
		}

		// if this is a top-level event, prepare a game state to look back to
		// for look-back-in-time triggered abilities
		GameState previousGameState = null;
		if(this.topLevel)
			// Changing this to simply use the actualState in-place causes tests
			// to fail. Why? When the game state is refreshed, a completely new
			// copy of the game state is created, which should leave this
			// "previous" state intact. -RulesGuru
			// Game.refreshActualState() clears the old actual state. -CommsGuy
			previousGameState = this.game.actualState.clone(true);

		java.util.List<Event> replacedEvents = checkReplacements();
		if(replacedEvents != null)
		{
			boolean status = true;
			Identity result = Identity.instance();
			// Set resultNow = new Set();
			for(Event replacedEvent: replacedEvents)
			{
				if(!replacedEvent.perform(this.parent, this.topLevel))
					status = false;

				// 614.11b If an effect would have a player both draw a card and
				// perform an additional action on that card, and the draw is
				// replaced, the additional action is not performed on any cards
				// that are drawn as a result of that replacement effect.
				if(this.type != EventType.DRAW_CARDS)
					result.addAll(replacedEvent.getResultGenerator());
			}

			this.setResult(result);
			afterPerformChecks(previousGameState);
			return status;
		}

		// go go go!
		java.util.Map<EventType.Parameter, Set> parametersNow = this.parametersNowAsSets();
		if(this.choices == null)
			this.type.makeChoices(this.game, this, parametersNow);
		boolean eventPerformed = this.type.perform(this.game, this, parametersNow);
		// There absolutely cannot be a refresh between here ...

		if(this.result == null)
			throw new IllegalStateException(this + " didn't define a result before it finished!");
		if(this.passResultID != -1)
		{
			Event toPassTo = this.game.physicalState.get(this.passResultID);
			toPassTo.setResult(this.result);
		}

		if(null != physicalEvent)
		{
			// This is used to support EventParameter. By storing the parameters
			// in the physical event, it is possible to reference them later.
			for(EventType.Parameter param: this.parameters.keySet())
				physicalEvent.parameters.put(param, this.parameters.get(param));
			physicalEvent.children = this.children;
		}

		// ... and here. If there is, continuous effects dependent on a tracker
		// created by the same ability as the continuous effect will expire
		// during said refresh, before the tracker gets a chance to update.
		if(eventPerformed)
			for(Tracker<?> flag: this.game.physicalState.getAllFlags())
				flag.register(this.game.physicalState, this);

		// become one of your parents children
		if(!this.topLevel || this.type == EventType.DRAW_ONE_CARD)
			this.parent.children.put(this, eventValid && eventPerformed);
		// Even if the event failed to pay a cost, refresh the actual state so
		// it exists in the actual state
		if(this.topLevel)
			this.game.refreshActualState();

		// if this was a cost and it didn't perform fully, fail out
		if(this.isCost && !eventPerformed)
			return false;

		afterPerformChecks(previousGameState);

		return eventValid && eventPerformed;
	}

	/**
	 * Performs this event.
	 * 
	 * @param parent What event is performing this.
	 * @param topLevel Whether this event is a "top level" event (a sentence on
	 * a card, for example)
	 * @return Whether this event can be used to pay a cost.
	 */
	public boolean perform(Event parent, boolean topLevel)
	{
		if(Game.debugging)
			System.out.println(this.type + ": " + this);

		this.topLevel = topLevel;

		if(parent != null)
		{
			// a child event can't be replaced by anything that replaced the
			// parent
			this.replacedBy.addAll(parent.replacedBy);

			// child events use the same event source as their parent if no
			// source has already been set
			if(-1 == this.sourceID)
				this.sourceID = parent.sourceID;

			this.parent = parent;
			this.isCost = this.isCost || parent.isCost;
			this.isEffect = this.isEffect && parent.isEffect;
			this.cantMoveIDs = parent.cantMoveIDs;
		}

		// perform the event
		boolean ret = this.perform();

		if(this.storeIn != -1)
		{
			// Get the physical link to store the information in
			Identified i = this.game.physicalState.get(this.storeIn);
			Linkable storeIn = null;
			if(i.isTriggeredAbility() || i.isActivatedAbility())
				storeIn = ((NonStaticAbility)i).getPrintedVersion(this.game.physicalState);
			else if(i.isStaticAbility())
				storeIn = (StaticAbility)(i.getPhysical());
			else
				throw new UnsupportedOperationException("Tried to store linked information in an unsupported type: " + i.getClass());

			for(Object o: this.getResult())
				storeIn.getLinkManager().addLinkInformation(o);
		}

		if(parent == null)
			this.clearParameters();
		return ret;
	}

	/**
	 * Tells this event that a player made a choice for it.
	 * 
	 * @param madeChoices Who made the choice.
	 * @param choices What things were chosen.
	 */
	public void putChoices(Player madeChoices, java.util.Collection<?> choices)
	{
		// This should be sufficient to initialize the choice map, since if no
		// player makes a choice for the event, this function should never be
		// called. If this becomes a problem, add a method to Event called
		// "makeChoices" that initializes this map and calls type.makeChoices,
		// then change outside calls to type.makeChoices to Event.makeChoices.
		if(this.choices == null)
			this.choices = new java.util.HashMap<Integer, Identity>();

		this.choices.put(madeChoices.ID, Identity.fromCollection(choices));
	}

	/**
	 * Check if any creatures aren't creatures anymore, or any planeswalkers
	 * aren't planeswalkers anymore. Also see if anything has changed control
	 * 
	 * @param previousGameState The game state to refer to when checking for
	 * lost types
	 */
	private void removeFromCombat(GameState previousGameState)
	{
		Set toRemoveFromCombat = new Set();
		for(GameObject newObject: this.game.actualState.getAllObjects())
		{
			GameObject oldObject = previousGameState.getByIDObject(newObject.ID);
			// The new object might not have existed in the old state
			if(null == oldObject)
				continue;

			if(this.game.actualState.currentPhase() != null && this.game.actualState.currentPhase().type == Phase.PhaseType.COMBAT)
			{
				// This is for creatures and planeswalkers that have lost
				// their type
				if(oldObject.getTypes().contains(Type.CREATURE) && !newObject.getTypes().contains(Type.CREATURE))
					toRemoveFromCombat.add(newObject);
				else if(oldObject.getTypes().contains(Type.PLANESWALKER) && !newObject.getTypes().contains(Type.PLANESWALKER))
					toRemoveFromCombat.add(newObject);
			}
		}

		if(!toRemoveFromCombat.isEmpty())
		{
			Event removeEvent = new Event(this.game.physicalState, "Remove " + toRemoveFromCombat + " from combat.", EventType.REMOVE_FROM_COMBAT);
			removeEvent.parameters.put(EventType.Parameter.OBJECT, Identity.fromCollection(toRemoveFromCombat));
			removeEvent.perform(this, false);
		}
	}

	/**
	 * Tells this event that it can "forget" all objects that were moved into
	 * the specified zone.
	 */
	public final void removeIndexedZone(Zone zone)
	{
		// only top-level events want this information
		if(!this.topLevel)
		{
			this.getTopLevelParent().removeIndexedZone(zone);
			return;
		}

		java.util.List<IndexedZone> toRemove = new java.util.LinkedList<IndexedZone>();
		for(IndexedZone indexedZone: this.objectsToOrderByChoice.keySet())
			if(indexedZone.zoneID == zone.ID)
				toRemove.add(indexedZone);
		// this is done in two steps to avoid modifying the list while the
		// iterator is using it
		for(IndexedZone indexedZone: toRemove)
			this.objectsToOrderByChoice.remove(indexedZone);
	}

	/**
	 * This figures out if any damage replacement effects can apply and, if so,
	 * applies one.
	 * 
	 * @return Whether any replacement effects applied
	 */
	private boolean replaceDamage()
	{
		// find all the things that could replace the damage in this
		// event
		java.util.Collection<DamageAssignment.Replacement> effects = new java.util.LinkedList<DamageAssignment.Replacement>();
		for(DamageReplacementEffect effect: this.game.actualState.damageReplacementEffects)
		{
			// if this effect is out of uses or damage, it can't do diddly
			FloatingContinuousEffect parentFCE = effect.getFloatingContinuousEffect(this.game.physicalState);
			if(null != parentFCE && (parentFCE.uses == 0 || parentFCE.damage == 0))
				continue;

			// if this effect has replaced some of the damage, don't try to
			// replace that damage again with this effect
			DamageAssignment.Batch relevantDamage = new DamageAssignment.Batch();
			for(DamageAssignment assignment: this.damage)
				if(!assignment.replacedBy.contains(effect))
					relevantDamage.add(assignment);
			DamageAssignment.Batch match = effect.match(this, relevantDamage);

			if(!match.isEmpty())
				effects.add(new DamageAssignment.Replacement(effect, match));
		}

		if(effects.isEmpty())
			return false;

		// figure out who cares
		java.util.Set<Player> affectedPlayers = new java.util.HashSet<Player>();
		for(DamageAssignment.Replacement replacement: effects)
		{
			DamageAssignment.Batch batch = replacement.batch;
			for(DamageAssignment assignment: batch)
			{
				Identified taker = this.state.get(assignment.takerID);
				if(taker.isPlayer())
					affectedPlayers.add((Player)taker);
				else
					affectedPlayers.add(((GameObject)taker).getController(this.game.actualState));
			}
		}

		// APNAP order
		java.util.List<Player> players = this.state.getPlayerCycle(this.game.actualState.currentTurn().getOwner(this.game.actualState));
		// the majority of this "loop" actually only runs once; the if at the
		// top of the loop selects which player will be choosing
		for(Player player: players)
		{
			if(!affectedPlayers.contains(player))
				continue;

			java.util.Collection<DamageAssignment.Replacement> choices = new java.util.LinkedList<DamageAssignment.Replacement>();
			for(DamageAssignment.Replacement replacement: effects)
			{
				// from this replacement's batch, remove all damage that does
				// not concern this player
				java.util.Collection<DamageAssignment> toRemove = new java.util.LinkedList<DamageAssignment>();
				for(DamageAssignment assignment: replacement.batch)
				{
					Identified taker = this.state.get(assignment.takerID);
					boolean damageToThisPlayer = (taker.isPlayer()) && player.equals(taker);
					boolean damageToSomethingThisPlayerControls = !taker.isPlayer() && !damageToThisPlayer && player.equals(((GameObject)taker).getController(this.game.actualState));
					if(!damageToThisPlayer && !damageToSomethingThisPlayerControls)
						toRemove.add(assignment);
				}
				replacement.batch.removeAll(toRemove);

				// if there is any damage in this replacement's batch that
				// concerns this player, offer them this choice
				if(!replacement.batch.isEmpty())
					choices.add(replacement);
			}

			DamageAssignment.Replacement choice;
			if(choices.size() == 1)
				choice = choices.iterator().next();
			else
				choice = player.sanitizeAndChoose(this.game.actualState, 1, choices, PlayerInterface.ChoiceType.REPLACEMENT_EFFECT, PlayerInterface.ChooseReason.REPLACE_DAMAGE).get(0);

			for(DamageAssignment damage: choice.batch)
				if(this.game.actualState.sourcesOfUnpreventableDamage.contains(this.state.get(damage.sourceID)))
					damage.isUnpreventable = true;

			// Remove the damage we're about to replace from this event
			this.damage.removeAll(choice.batch);

			// Replace the damage, performing any events that result from those
			// replacements
			java.util.List<EventFactory> events = new java.util.LinkedList<EventFactory>();
			int prevented = choice.effect.apply(choice.batch, player, events);

			// if this effect is out of uses, it can't do diddly
			FloatingContinuousEffect parentFCE = choice.effect.getFloatingContinuousEffect(this.game.actualState);
			if(null != parentFCE && parentFCE.uses > 0 && (!choice.effect.isPreventionEffect() || prevented > 0))
				--parentFCE.getPhysical().uses;

			for(EventFactory factory: events)
			{
				Event e = factory.createEvent(this.game, (GameObject)choice.effect.getSourceObject(this.game.actualState));

				for(DamageAssignment assignment: choice.batch)
					e.replacedBy.addAll(assignment.replacedBy);
				e.replacedBy.add(choice.effect);
				e.perform(this, false);
			}

			// Add the replaced damage back to this event
			this.damage.addAll(choice.batch);

			break;
		}
		return true;
	}

	private ZoneChangeReplacementEffect replaceZoneChanges()
	{
		{
			Identified thisObject = this.getSource();
			java.util.Collection<ZoneChange> toRemove = new java.util.LinkedList<ZoneChange>();
			for(ZoneChange zoneChange: this.zoneChanges)
				for(ZoneChangePattern prohibition: this.game.actualState.zoneChangeProhibitions)
					if(prohibition.match(zoneChange, thisObject, this.game.actualState))
						toRemove.add(zoneChange);
			this.zoneChanges.removeAll(toRemove);
		}

		// find all the things that could replace the zone changes in this event
		java.util.Collection<ZoneChange.Replacement> replacements = new java.util.LinkedList<ZoneChange.Replacement>();
		for(ZoneChangeReplacementEffect effect: this.game.actualState.zoneChangeReplacementEffects)
		{
			// if this effect is out of uses, it can't do diddly
			FloatingContinuousEffect parentFCE = effect.getFloatingContinuousEffect(this.game.actualState);
			if(null != parentFCE && parentFCE.uses == 0)
				continue;

			// if this effect has replaced some of the zone-changes, don't try
			// to replace that zone-change again with this effect
			java.util.Collection<ZoneChange> relevantZoneChanges = new java.util.LinkedList<ZoneChange>();
			for(ZoneChange change: this.zoneChanges)
				if(!change.replacedBy.contains(effect))
					relevantZoneChanges.add(change);

			for(ZoneChange change: effect.match(this.game.actualState, relevantZoneChanges))
				replacements.add(new ZoneChange.Replacement(effect, change));
		}

		if(replacements.isEmpty())
			return null;

		// figure out who cares
		java.util.Set<Player> affectedPlayers = new java.util.HashSet<Player>();
		for(ZoneChange.Replacement replacement: replacements)
			affectedPlayers.add(this.game.actualState.<GameObject>get(replacement.zoneChange.oldObjectID).getController(this.game.actualState));

		// APNAP order
		java.util.List<Player> players = this.state.players;
		if(this.game.hasStarted())
			players = this.state.getPlayerCycle(this.game.actualState.currentTurn().getOwner(this.game.actualState));
		// the majority of this "loop" actually only runs once; the if at the
		// top of the loop selects which player will be choosing
		for(Player player: players)
		{
			if(!affectedPlayers.contains(player))
				continue;

			java.util.Collection<ZoneChange.Replacement> choices = new java.util.LinkedList<ZoneChange.Replacement>();
			for(ZoneChange.Replacement replacement: replacements)
				if(this.game.actualState.<GameObject>get(replacement.zoneChange.oldObjectID).getController(this.game.actualState).equals(player))
					choices.add(replacement);

			// 616.1b If any of the replacement and/or prevention effects would
			// modify under whose control an object would enter the battlefield,
			// one of them must be chosen.
			boolean gatherSpecimens = false;
			boolean clone = false;
			for(ZoneChange.Replacement available: choices)
			{
				if(available.effect.isGatherSpecimensEffect())
				{
					gatherSpecimens = true;
					break;
				}
				else if(available.effect.isCloneEffect())
					clone = true; // no 'break' statement since we might still
									// have a gather specimens
			}
			if(gatherSpecimens)
			{
				java.util.Iterator<ZoneChange.Replacement> i = choices.iterator();
				while(i.hasNext())
					if(!i.next().effect.isGatherSpecimensEffect())
						i.remove();
			}
			else if(clone)
			{
				java.util.Iterator<ZoneChange.Replacement> i = choices.iterator();
				while(i.hasNext())
					if(!i.next().effect.isCloneEffect())
						i.remove();
			}

			ZoneChange.Replacement choice;
			if(choices.size() == 1)
				choice = choices.iterator().next();
			else
				choice = player.sanitizeAndChoose(this.game.actualState, 1, choices, PlayerInterface.ChoiceType.REPLACEMENT_EFFECT, PlayerInterface.ChooseReason.REPLACE_ZONE_CHANGE).get(0);

			// if this effect is out of uses, it can't do diddly
			FloatingContinuousEffect parentFCE = choice.effect.getFloatingContinuousEffect(this.game.actualState);
			if(null != parentFCE && parentFCE.uses > 0)
				--parentFCE.getPhysical().uses;

			// Remove the zone-changes we're about to replace from this event
			this.zoneChanges.remove(choice.zoneChange);

			// Replace the zone-changes, performing any events that result from
			// those replacements
			java.util.Collection<ZoneChange> singleChange = new java.util.LinkedList<ZoneChange>();
			singleChange.add(choice.zoneChange);
			for(EventFactory factory: choice.effect.apply(singleChange))
			{
				Event e = factory.createEvent(this.game, (GameObject)choice.effect.getSourceObject(this.game.actualState));

				e.replacedBy.addAll(choice.zoneChange.replacedBy);
				e.replacedBy.add(choice.effect);
				e.forceZoneChanges = true;
				e.perform(this, true);
			}

			// Add the replaced zone-changes back to this event
			this.zoneChanges.addAll(singleChange);
			return choice.effect;
		}
		return null;
	}

	@Override
	public java.io.Serializable sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedEvent(state.<Event>get(this.ID));
	}

	/** Tells this Event what result it has. */
	public void setResult(Identity result)
	{
		this.result = result;
	}

	/** Tells this Event what result it has. */
	public void setResult(Set result)
	{
		this.result = Identity.fromCollection(result);
	}

	/** Tells this Event what it came from. */
	public void setSource(GameObject source)
	{
		if(source == null)
			this.sourceID = -1;
		else
		{
			// If this object isn't in a zone, and has been in a zone before,
			// then it's in the void. Set the sourceID to be the "right" object
			// -- that is, the one this object was created from.
			if(source.zoneID == -1 && source.pastSelf != -1)
				this.sourceID = source.pastSelf;
			else
				this.sourceID = source.ID;
		}
	}

	/**
	 * Set the object to store the results of this event in.
	 */
	public void setStoreInObject(Linkable link)
	{
		this.storeIn = ((Identified)link).ID;
	}

	private void shuffle(int libraryID)
	{
		Zone library = this.game.physicalState.get(libraryID);
		for(int i = 0; i < library.objects.size(); ++i)
		{
			GameObject oldObject = library.objects.get(i);
			oldObject.ghost();

			// Ghosting the object replaces the physical object, so call
			// getPhysical to get the new one.
			GameObject newObject = oldObject.getPhysical().createToMove(library);
			newObject.zoneID = oldObject.zoneID;
			library.objects.set(i, newObject);
		}

		if(!this.game.noRandom)
			java.util.Collections.shuffle(library.objects);
	}

	/**
	 * Evaluates the parameters of this event and removes all ghosts from the
	 * "affected" parameter.
	 * 
	 * @return If the event was changed in this manner, false; otherwise true.
	 */
	private boolean validate()
	{
		boolean removedGhost = false;
		GameObject eventSource = this.getSource();

		// initialize the parameters now map
		this.parametersNow = new java.util.HashMap<EventType.Parameter, Identity>();
		for(EventType.Parameter parameterName: this.parameters.keySet())
		{
			// evaluate one parameter
			Set newParameter = this.parameters.get(parameterName).evaluate(this.game, eventSource);

			// events can't "act on" things that aren't there, so if this
			// parameter is the "affected" parameter...
			if(parameterName == this.type.affects())
			{
				// ... remove all ghosts from this parameter
				java.util.Set<GameObject> toRemove = new java.util.HashSet<GameObject>();
				for(GameObject o: newParameter.getAll(GameObject.class))
					if(o.isGhost())
						toRemove.add(o);
				if(!toRemove.isEmpty())
				{
					removedGhost = true;
					newParameter.removeAll(toRemove);
				}
			}
			this.parametersNow.put(parameterName, Identity.fromCollection(newParameter));
		}
		return !removedGhost;
	}

	/** @return Whether this event's perform method was called. */
	public boolean wasPerformed()
	{
		return this.result != null;
	}
}
