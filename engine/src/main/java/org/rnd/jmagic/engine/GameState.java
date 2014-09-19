package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.patterns.*;

/** Represents the game state. */
public class GameState implements Cloneable
{
	/**
	 * Represents a delayed one-shot effect, used in "exile until" and similar
	 * effects. Consists of an ID, a SetGenerator and an EventFactory, so this
	 * is freely copiable between states.
	 */
	private static class DelayedOneShot
	{
		private static int nextDosID = 0;

		public int dosID;
		public int sourceID;
		public SetGenerator condition;
		public EventFactory effect;

		public DelayedOneShot(int sourceID, SetGenerator condition, EventFactory effect)
		{
			this.dosID = nextDosID++;
			this.sourceID = sourceID;
			this.condition = condition;
			this.effect = effect;
		}

		@Override
		public int hashCode()
		{
			return dosID;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			DelayedOneShot other = (DelayedOneShot)obj;
			if(dosID != other.dosID)
				return false;
			return true;
		}
	}

	public java.util.Map<String, java.util.Map<Integer, Set>> abilityExemptions;

	/** The Doran flag. */
	public boolean assignCombatDamageUsingToughness;

	public java.util.Collection<AttackingCost> attackingCosts;

	public java.util.Collection<AttackingRequirement> attackingRequirements;
	public java.util.Collection<CombatRestriction> attackingRestrictions;

	private int battlefieldID;

	public java.util.Collection<BlockingCost> blockingCosts;

	public java.util.Collection<BlockingRequirement> blockingRequirements;
	public java.util.Collection<CombatRestriction> blockingRestrictions;

	private int commandZoneID;

	/** keys are controlled players, values are controlling players */
	protected java.util.Map<Integer, Integer> controlledPlayers;

	public java.util.List<NonStaticAbility> currentlyResolvingManaAbilities;

	private Phase currentPhase;

	private Step currentStep;

	private Turn currentTurn;

	public java.util.Collection<DamageReplacementEffect> damageReplacementEffects;

	/**
	 * keys are IDs of source GameObjects
	 */
	public java.util.Map<Integer, ContinuousEffectType.DamageAbility> dealDamageAsThoughHasAbility;

	/**
	 * If not -1, this overrides who chooses attackers and what to attack with
	 * the ID of the player who should be choosing instead.
	 */
	public int declareAttackersPlayerOverride;

	/**
	 * If not -1, this overrides who chooses blockers and what to block with the
	 * ID of the player who should be choosing instead.
	 */
	public int declareBlockersPlayerOverride;

	public java.util.Collection<DelayedTrigger> delayedTriggers;

	/**
	 * delayed one shots waiting to occur. directly copiable (see
	 * {@link DelayedOneShot})
	 */
	public java.util.Collection<DelayedOneShot> delayedOneShots;

	public java.util.Collection<EventReplacementEffect> eventReplacementEffects;

	public java.util.Collection<EventReplacementEffectStopper> eventReplacementEffectStoppers;
	public java.util.Collection<EventTriggeredAbilityStopper> eventTriggeredAbilityStoppers;

	private int exileZoneID;

	/** Should only be added to by ContinuousEffectType.ADD_UNTAP_EVENT */
	public java.util.List<Event> extraEvents;

	private java.util.Map<Class<? extends Tracker<?>>, Tracker<?>> flags;

	public java.util.List<FloatingContinuousEffect> floatingEffects;

	public java.util.List<Turn> futureTurns;

	public Game game;

	public java.util.Set<org.rnd.jmagic.engine.generators.HasName.HasNameModifier> hasNameModifiers;

	private java.util.Map<Integer, Identified> identifieds;

	/**
	 * This map describes cost additions for playing spells and abilities. The
	 * keys to the map are sets that describe which spells and abilities the
	 * cost applies to; the values are the costs.
	 */
	public java.util.Map<Set, ManaPool> manaCostAdditions;

	/**
	 * This map describes cost minimums for playing spells and abilities. The
	 * keys to the map are sets that describe which spells and abilities the
	 * cost applies to; the values are the minimums. (Trinisphere)
	 */
	public java.util.Map<Set, Integer> manaCostMinimums;

	/**
	 * This map describes cost reductions for playing spells and abilities. The
	 * keys to the map are sets that describe which spells and abilities the
	 * reduction applies to; the values are the costs.
	 */
	public java.util.Map<Set, ManaPool> manaCostReductions;

	/**
	 * This map describes cost reductions for playing spells and abilities. The
	 * keys to the map are sets that describe which spells and abilities the
	 * reduction applies to; the values are the costs.
	 */
	public java.util.Map<Set, ManaPool> manaCostColoredReductions;

	/**
	 * Like manaCostReductions except that these effects can't reduce the amount
	 * of mana something costs to play to less than one mana.
	 */
	public java.util.Map<Set, ManaPool> manaCostRestrictedReductions;

	/**
	 * Keys are player IDs; values are patterns describing what kind of mana
	 * doesn't empty from that players mana pool as steps and phases end.
	 */
	public java.util.Map<Integer, MultipleSetPattern> manaThatDoesntEmpty;

	private int nextTimestamp;

	/** What actions the acting player is allowed to take. */
	public java.util.Set<PlayerAction> playerActions;

	public java.util.List<Player> players;

	public int playerWithPriorityID;

	public int playingFirstID;

	private Step previousStep;

	public java.util.Collection<EventPattern> eventProhibitions;
	public java.util.Collection<PlayProhibition> playProhibitions;
	public java.util.Collection<ZoneChangePattern> zoneChangeProhibitions;

	/**
	 * The object that is currently resolving. ... If you aren't writing the
	 * resolve() method of a child of GameObject, please don't touch this.
	 */
	public int resolvingID;

	public java.util.Collection<GameObject> sourcesOfUnpreventableDamage;

	public java.util.Map<SpecialActionFactory, GameObject> specialActionFactories;

	private int stackID;

	/**
	 * Map from player ID to collection of object ID representing the objects
	 * that, if they are creatures, "have summoning sickness".
	 */
	public java.util.Map<Integer, java.util.Collection<Integer>> summoningSick;

	public IDList<GameObject> voidedObjects;

	/** Triggered abilities that have triggered but not gone on the stack. */
	public java.util.Map<Integer, java.util.Collection<TriggeredAbility>> waitingTriggers;

	public java.util.Collection<ZoneChangeReplacementEffect> zoneChangeReplacementEffects;

	/** Constructs a shiny new game state. */
	public GameState(Game game)
	{
		// initialize this first because other things require it
		this.identifieds = new java.util.HashMap<Integer, Identified>();
		this.game = game;

		this.abilityExemptions = new java.util.HashMap<String, java.util.Map<Integer, Set>>();
		this.assignCombatDamageUsingToughness = false;
		this.attackingCosts = new java.util.LinkedList<AttackingCost>();
		this.attackingRequirements = new java.util.LinkedList<AttackingRequirement>();
		this.attackingRestrictions = null;
		this.battlefieldID = new Zone(this, "The Battlefield").ID;
		this.blockingCosts = new java.util.LinkedList<BlockingCost>();
		this.blockingRequirements = new java.util.LinkedList<BlockingRequirement>();
		this.blockingRestrictions = null;
		this.commandZoneID = new Zone(this, "The Command Zone").ID;
		this.controlledPlayers = new java.util.HashMap<Integer, Integer>();
		this.currentlyResolvingManaAbilities = new java.util.Stack<NonStaticAbility>();
		this.currentPhase = null;
		this.currentStep = null;
		this.currentTurn = null;
		this.damageReplacementEffects = new java.util.LinkedList<DamageReplacementEffect>();
		this.dealDamageAsThoughHasAbility = new java.util.HashMap<Integer, ContinuousEffectType.DamageAbility>();
		this.delayedTriggers = new java.util.LinkedList<DelayedTrigger>();
		this.delayedOneShots = new java.util.LinkedList<DelayedOneShot>();
		this.declareAttackersPlayerOverride = -1;
		this.declareBlockersPlayerOverride = -1;
		this.eventProhibitions = new java.util.LinkedList<EventPattern>();
		this.eventReplacementEffects = new java.util.LinkedList<EventReplacementEffect>();
		this.eventReplacementEffectStoppers = new java.util.LinkedList<EventReplacementEffectStopper>();
		this.eventTriggeredAbilityStoppers = new java.util.LinkedList<EventTriggeredAbilityStopper>();
		this.exileZoneID = new Zone(this, "The Exile Zone").ID;
		this.extraEvents = new java.util.LinkedList<Event>();
		this.flags = new java.util.HashMap<Class<? extends Tracker<?>>, Tracker<?>>();
		this.floatingEffects = new java.util.LinkedList<FloatingContinuousEffect>();
		this.futureTurns = new java.util.LinkedList<Turn>();
		this.hasNameModifiers = null;
		this.manaCostAdditions = new java.util.HashMap<Set, ManaPool>();
		this.manaCostMinimums = new java.util.HashMap<Set, Integer>();
		this.manaCostReductions = new java.util.HashMap<Set, ManaPool>();
		this.manaCostColoredReductions = new java.util.HashMap<Set, ManaPool>();
		this.manaCostRestrictedReductions = new java.util.HashMap<Set, ManaPool>();
		this.manaThatDoesntEmpty = new java.util.HashMap<Integer, MultipleSetPattern>();
		this.nextTimestamp = 0;
		this.playerActions = new java.util.HashSet<PlayerAction>();
		this.playerWithPriorityID = -1;
		this.players = new IDList<Player>(this);
		this.playingFirstID = -1;
		this.playProhibitions = new java.util.LinkedList<>();
		this.previousStep = null;
		this.resolvingID = -1;
		this.sourcesOfUnpreventableDamage = new java.util.LinkedList<GameObject>();
		this.specialActionFactories = new java.util.HashMap<SpecialActionFactory, GameObject>();
		this.stackID = new Zone(this, "The Stack").ID;
		this.summoningSick = new java.util.HashMap<Integer, java.util.Collection<Integer>>();
		this.voidedObjects = new IDList<GameObject>(this);
		this.waitingTriggers = new java.util.HashMap<Integer, java.util.Collection<TriggeredAbility>>();
		this.zoneChangeProhibitions = new java.util.LinkedList<ZoneChangePattern>();
		this.zoneChangeReplacementEffects = new java.util.LinkedList<ZoneChangeReplacementEffect>();

		// The set of Trackers that the game always needs
		this.ensureTracker(new SuccessfullyAttacked());
		this.ensureTracker(new org.rnd.jmagic.engine.generators.LandsPlayedThisTurn.LandsPlayedTracker());
	}

	/**
	 * Adds a delayed one-shot effect (like those used in "exile until" effects)
	 * to this game state.
	 * 
	 * @param source The source of the effect, which is the same as the source
	 * of the original effect.
	 * @param condition The "event" after which we perform the delayed effect --
	 * this is a set generator because our only way of tracking past events is
	 * via trackers, and we need to track past events because of a quirk in the
	 * delayed-one-shot rules.
	 * @param effect The effect to perform.
	 */
	public void addDelayedOneShot(GameObject source, SetGenerator condition, EventFactory effect)
	{
		this.delayedOneShots.add(new DelayedOneShot(source.ID, condition, effect));
	}

	/**
	 * Adds an extra turn (e.g., from Time Walk) to this game state.
	 * 
	 * @param owner Who takes the extra turn.
	 */
	public Turn addExtraTurn(Player owner)
	{
		Turn extraTurn = new Turn(owner);
		extraTurn.extra = true;
		this.futureTurns.add(0, extraTurn);
		return extraTurn;
	}

	/**
	 * @param player The player to add to this game state.
	 */
	public void addPlayer(Player player)
	{
		this.players.add(player);

		this.manaThatDoesntEmpty.put(player.ID, new MultipleSetPattern(false));
		this.summoningSick.put(player.ID, new java.util.LinkedList<Integer>());
		this.waitingTriggers.put(player.ID, new IDList<TriggeredAbility>(this));
		this.battlefield().visibleTo.add(player.ID);
		this.exileZone().visibleTo.add(player.ID);
		this.stack().visibleTo.add(player.ID);

		for(Player p: this.players)
		{
			p.getGraveyard(this).visibleTo.add(player.ID);
			player.getGraveyard(this).visibleTo.add(p.ID);
		}

		player.getHand(this).visibleTo.add(player.ID);
		player.getSideboard(this).visibleTo.add(player.ID);
	}

	public java.util.List<Player> apnapOrder(Set players)
	{
		if(!this.game.hasStarted())
			throw new UnsupportedOperationException("Can't get APNAP order before the game starts");

		java.util.List<Player> ret = new java.util.LinkedList<Player>();
		Turn t = this.currentTurn();
		Player activePlayer = t.getOwner(this);
		if(players.contains(activePlayer))
			ret.add(activePlayer);

		for(Turn turn: this.futureTurns)
		{
			if(turn.extra)
				continue;

			Player nonActivePlayer = turn.getOwner(this);
			if(players.contains(nonActivePlayer))
				ret.add(nonActivePlayer);
		}

		return ret;
	}

	public Zone battlefield()
	{
		return this.get(this.battlefieldID);
	}

	public void clear()
	{
		for(Identified i: this.identifieds.values())
			if(i.state == this)
				i.state = null;
		this.identifieds.clear();

		this.abilityExemptions.clear();
		this.attackingCosts.clear();
		this.attackingRequirements.clear();
		if(null != this.attackingRestrictions)
			this.attackingRestrictions.clear();
		this.blockingCosts.clear();
		this.blockingRequirements.clear();
		if(null != this.blockingRestrictions)
			this.blockingRestrictions.clear();
		this.currentlyResolvingManaAbilities.clear();
		this.damageReplacementEffects.clear();
		this.delayedTriggers.clear();
		this.eventReplacementEffects.clear();
		this.eventReplacementEffectStoppers.clear();
		this.eventTriggeredAbilityStoppers.clear();
		this.extraEvents.clear();
		this.floatingEffects.clear();
		this.futureTurns.clear();
		this.manaCostAdditions.clear();
		this.manaCostReductions.clear();
		this.manaCostColoredReductions.clear();
		this.manaCostRestrictedReductions.clear();
		this.playerActions.clear();
		this.players.clear();
		this.playProhibitions.clear();
		this.eventProhibitions.clear();
		this.sourcesOfUnpreventableDamage.clear();
		this.specialActionFactories.clear();
		this.summoningSick.clear();
		this.waitingTriggers.clear();
		this.zoneChangeProhibitions.clear();
		this.zoneChangeReplacementEffects.clear();
	}

	/**
	 * Java-copies this game state. A complete copy can only be made from a
	 * complete GameState.
	 * 
	 * @param complete Whether or not to make a complete copy or a "shallow"
	 * copy (where Identified instances aren't copied until they are modified)
	 */
	public GameState clone(boolean complete)
	{
		try
		{
			GameState ret = (GameState)super.clone();
			if(complete)
			{
				ret.identifieds = new java.util.HashMap<Integer, Identified>();

				for(java.util.Map.Entry<Integer, Identified> e: this.identifieds.entrySet())
					e.getValue().clone(ret);
			}
			else
			{
				ret.identifieds = new java.util.HashMap<Integer, Identified>(this.identifieds);

				for(Zone zone: ret.getAll(Zone.class))
					ret.identifieds.put(zone.ID, zone.clone(ret));
			}

			ret.currentlyResolvingManaAbilities = new IDList<NonStaticAbility>(ret, this.currentlyResolvingManaAbilities);
			ret.currentPhase = this.currentPhase;
			ret.currentStep = this.currentStep;
			ret.currentTurn = this.currentTurn;
			ret.delayedOneShots = new java.util.LinkedList<DelayedOneShot>(this.delayedOneShots);
			ret.delayedTriggers = new IDList<DelayedTrigger>(ret, this.delayedTriggers);
			ret.exileZoneID = this.exileZoneID;
			ret.flags = new java.util.HashMap<Class<? extends Tracker<?>>, Tracker<?>>();
			for(java.util.Map.Entry<Class<? extends Tracker<?>>, Tracker<?>> entry: this.flags.entrySet())
				ret.flags.put(entry.getKey(), entry.getValue().clone());
			ret.floatingEffects = new IDList<FloatingContinuousEffect>(ret, this.floatingEffects);
			ret.futureTurns = new java.util.LinkedList<Turn>(this.futureTurns);
			ret.battlefieldID = this.battlefieldID;
			ret.nextTimestamp = this.nextTimestamp;
			ret.previousStep = this.previousStep;
			ret.players = new IDList<Player>(ret, this.players);
			ret.resolvingID = this.resolvingID;
			ret.stackID = this.stackID;
			ret.summoningSick = new java.util.HashMap<Integer, java.util.Collection<Integer>>();
			for(Integer i: this.summoningSick.keySet())
			{
				java.util.Collection<Integer> objects = new java.util.LinkedList<Integer>();
				for(Integer j: this.summoningSick.get(i))
					objects.add(j);

				ret.summoningSick.put(i, objects);
			}
			ret.voidedObjects = new IDList<GameObject>(ret, this.voidedObjects);
			ret.waitingTriggers = new java.util.HashMap<Integer, java.util.Collection<TriggeredAbility>>();
			for(Integer i: this.waitingTriggers.keySet())
				ret.waitingTriggers.put(i, new IDList<TriggeredAbility>(ret, this.waitingTriggers.get(i)));

			// clone() should only ever be called on the physical state and,
			// since the following fields are always empty in the physical
			// state, don't copy them
			ret.abilityExemptions = new java.util.HashMap<String, java.util.Map<Integer, Set>>();
			ret.assignCombatDamageUsingToughness = false;
			ret.attackingCosts = new java.util.LinkedList<AttackingCost>();
			ret.attackingRequirements = new java.util.LinkedList<AttackingRequirement>();
			ret.attackingRestrictions = new java.util.HashSet<CombatRestriction>();
			ret.blockingCosts = new java.util.LinkedList<BlockingCost>();
			ret.blockingRequirements = new java.util.LinkedList<BlockingRequirement>();
			ret.blockingRestrictions = new java.util.HashSet<CombatRestriction>();
			ret.controlledPlayers = new java.util.HashMap<Integer, Integer>();
			ret.damageReplacementEffects = new java.util.LinkedList<DamageReplacementEffect>();
			ret.dealDamageAsThoughHasAbility = new java.util.HashMap<Integer, ContinuousEffectType.DamageAbility>();
			ret.eventReplacementEffects = new java.util.LinkedList<EventReplacementEffect>();
			ret.eventReplacementEffectStoppers = new java.util.LinkedList<EventReplacementEffectStopper>();
			ret.eventTriggeredAbilityStoppers = new java.util.LinkedList<EventTriggeredAbilityStopper>();
			ret.extraEvents = new java.util.LinkedList<Event>();
			ret.hasNameModifiers = new java.util.HashSet<org.rnd.jmagic.engine.generators.HasName.HasNameModifier>();
			ret.manaCostAdditions = new java.util.HashMap<Set, ManaPool>();
			ret.manaCostMinimums = new java.util.HashMap<Set, Integer>();
			ret.manaCostReductions = new java.util.HashMap<Set, ManaPool>();
			ret.manaCostColoredReductions = new java.util.HashMap<Set, ManaPool>();
			ret.manaCostRestrictedReductions = new java.util.HashMap<Set, ManaPool>();
			ret.manaThatDoesntEmpty = new java.util.HashMap<Integer, MultipleSetPattern>();
			for(int key: this.manaThatDoesntEmpty.keySet())
				ret.manaThatDoesntEmpty.put(key, new MultipleSetPattern(false));
			ret.playerActions = new java.util.HashSet<PlayerAction>();
			ret.eventProhibitions = new java.util.LinkedList<EventPattern>();
			ret.playProhibitions = new java.util.LinkedList<>();
			ret.zoneChangeProhibitions = new java.util.LinkedList<ZoneChangePattern>();
			ret.sourcesOfUnpreventableDamage = new java.util.LinkedList<GameObject>();
			ret.specialActionFactories = new java.util.HashMap<SpecialActionFactory, GameObject>();
			ret.zoneChangeReplacementEffects = new java.util.LinkedList<ZoneChangeReplacementEffect>();

			return ret;
		}
		catch(CloneNotSupportedException e)
		{
			throw new InternalError();
		}
	}

	public Zone commandZone()
	{
		return this.get(this.commandZoneID);
	}

	/**
	 * @return Whether this game state knows about an Identified with the
	 * specified ID.
	 */
	public boolean containsIdentified(int ID)
	{
		return this.identifieds.containsKey(ID);
	}

	/**
	 * Copy an {@link Identified} from a different GameState into this one by
	 * cloning it.
	 * 
	 * @param i The Identified to clone. It must have been cloned from the
	 * GameState which cloned this GameState.
	 * @return The new Identified
	 */
	public <T extends Identified> T copyForEditing(T i)
	{
		if(this == i.state)
			return i;

		T i2 = this.get(i.ID);
		if(this == i2.state)
			return i2;

		@SuppressWarnings("unchecked") T cloned = (T)i.clone(this);
		return cloned;
	}

	public Phase currentPhase()
	{
		return this.currentPhase;
	}

	public Step currentStep()
	{
		return this.currentStep;
	}

	public Turn currentTurn()
	{
		return this.currentTurn;
	}

	@SuppressWarnings("unchecked")
	public void ensureTracker(Tracker<?> tracker)
	{
		if(!this.flags.containsKey(tracker.getClass()))
			this.flags.put((Class<Tracker<?>>)tracker.getClass(), tracker);
	}

	public Zone exileZone()
	{
		return this.get(this.exileZoneID);
	}

	/**
	 * fires waiting delayed one-shot effects (like those created by
	 * "exile until" effects)
	 */
	public void fireDelayedOneShots()
	{
		// we are directly grabbing the physical state's DOSs here. this is not
		// a mistake.

		// we need to remove the DOS from the state *before* it fires, and that
		// change must propagate down, else firing this event will cause the
		// resulting eventually-recursive call of this method to fire it again.

		// we could do a little more work here, to use the delayedOneShots from
		// the actual state but remove them from the physical when they fire,
		// but WotC hasn't gone batshit enough to have an effect modify
		// waiting one-shot effects (*shudder*).

		java.util.Iterator<DelayedOneShot> iterator = this.game.physicalState.delayedOneShots.iterator();
		while(iterator.hasNext())
		{
			DelayedOneShot effect = iterator.next();
			GameObject source = this.get(effect.sourceID);
			if(effect.condition.evaluate(this, source).isEmpty())
				continue;

			iterator.remove();
			effect.effect.createEvent(this.game, source).perform(null, true);
		}
	}

	/**
	 * Gets the element out of the identified map with the specified ID.
	 * 
	 * @param <T> The type of element to get.
	 * @param ID The ID of the element to get.
	 * @return If an element of the specified type with the specified ID exists
	 * in the map, that element.
	 * @throws ClassCastException if an element of the specified ID exists but
	 * is of the wrong type
	 * @throws NullPointerException if no element of the specified ID exists
	 */
	@SuppressWarnings("unchecked")
	public <T extends Identified> T get(int ID)
	{
		if(this.identifieds.containsKey(ID))
			return (T)(this.identifieds.get(ID));
		throw new NullPointerException("Element with ID " + ID + " not found!");
	}

	@SuppressWarnings("unchecked")
	public <T> Iterable<T> getAll(final Class<T> cls)
	{
		java.util.Set<T> ret = new java.util.HashSet<T>();
		for(Identified i: GameState.this.identifieds.values())
			if(cls.isAssignableFrom(i.getClass()))
				ret.add((T)i);
		return ret;
	}

	public java.util.Collection<Tracker<?>> getAllFlags()
	{
		return this.flags.values();
	}

	/**
	 * @return All objects which are not LKI-ghosts that exist in this state.
	 * This iterable will be invalidated if any Identifieds are added to or
	 * removed from the state, whether those Identifieds are GameObjects.
	 */
	public Iterable<GameObject> getAllObjects()
	{
		java.util.Set<GameObject> ret = new java.util.HashSet<GameObject>();
		for(Identified i: GameState.this.identifieds.values())
			if(i.isGameObject() && !((GameObject)i).isGhost())
				ret.add((GameObject)i);
		return ret;
	}

	/**
	 * @param ID ID of the object to get
	 * @return The object with the specified ID, if such an object exists and is
	 * not a ghost. Null if the object does not exist or is a ghost.
	 * @throws ClassCastException If an Identified with the specified ID exists
	 * but is not a GameObject.
	 */
	public GameObject getByIDObject(int ID)
	{
		if(this.containsIdentified(ID))
		{
			GameObject ret = this.get(ID);
			if(ret.isGhost())
				return null;
			return ret;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tracker<?>> T getTracker(Class<T> clazz)
	{
		Tracker<?> flag = this.flags.get(clazz);
		if(flag == null)
			throw new Tracker.TrackerNotFoundException(clazz);
		return (T)flag;
	}

	/** @return The next available timestamp for effects in this game state. */
	public int getNextAvailableTimestamp()
	{
		return this.nextTimestamp++;
	}

	/**
	 * @param firstPlayer Which player to put first in the list.
	 * @return The players in this state, in seat order, with the specified
	 * player first.
	 */
	public java.util.List<Player> getPlayerCycle(Player firstPlayer)
	{
		if(null == firstPlayer)
			throw new NullPointerException("Can't cycle player list to a null first player");

		java.util.List<Player> ret = new java.util.LinkedList<Player>(this.players);
		if(ret.size() > 0)
			while(ret.get(0).ID != firstPlayer.ID)
				ret.add(ret.remove(0));
		return ret;
	}

	/** @return The player who has priority; null if no one does. */
	public Player getPlayerWithPriority()
	{
		if(!this.containsIdentified(this.playerWithPriorityID))
			return null;
		return this.get(this.playerWithPriorityID);
	}

	public boolean isAttackingRestrictionViolated()
	{
		for(CombatRestriction restriction: this.attackingRestrictions)
			if(!restriction.check(this))
				return true;
		return false;
	}

	public boolean isBlockingRestrictionViolated()
	{
		for(CombatRestriction restriction: this.blockingRestrictions)
			if(!restriction.check(this))
				return true;
		return false;
	}

	/**
	 * Copies the current turn and puts the copy on the end of the turn list,
	 * (doesn't happen if the current turn is null; i.e., the "next" turn is the
	 * first turn of the game).
	 * 
	 * @return the next turn to be played.
	 */
	public Turn nextTurn()
	{
		if(this.currentTurn != null)
		{
			Player owner = this.currentTurn.getOwner(this);
			if(this.players.contains(owner) && !this.currentTurn.extra)
				this.futureTurns.add(new Turn(owner));
		}
		return this.futureTurns.remove(0);
	}

	/** @return The number of players playing this game. */
	public int numPlayers()
	{
		int remainingPlayers = 0;
		for(Player p: this.players)
			if(!(p.outOfGame))
				++remainingPlayers;
		return remainingPlayers;
	}

	public Step previousStep()
	{
		return this.previousStep;
	}

	public void put(Identified i)
	{
		Identified old = this.identifieds.put(i.ID, i);
		if(null != old && old != i && old.state == this)
			old.state = null;
	}

	public Identified removeIdentified(int ID)
	{
		Identified old = this.identifieds.get(ID);
		if(null != old && old.state == this)
			old.state = null;
		return this.identifieds.remove(ID);
	}

	/**
	 * Removes several players from this game. Also removes all turns that those
	 * players would take and ends the game if there are fewer than two players
	 * left.
	 * 
	 * @param players The players to remove.
	 */
	public void removePlayers(java.util.Collection<Player> players)
	{
		if(players.isEmpty())
			return;

		for(Player player: players)
		{
			player.getPhysical().outOfGame = true;
			player.outOfGame = true;
		}

		for(Player player: players)
		{
			this.manaThatDoesntEmpty.remove(player.ID);
			this.summoningSick.remove(player.ID);
			this.waitingTriggers.remove(player.ID);

			java.util.Iterator<Turn> turnIterator = this.futureTurns.iterator();
			while(turnIterator.hasNext())
			{
				Turn turn = turnIterator.next();
				if(turn.ownerID == player.ID)
					turnIterator.remove();
			}
		}

		this.game.refreshActualState();
	}

	public void setCurrentPhase(Phase phase)
	{
		this.currentPhase = phase;
	}

	public void setCurrentStep(Step step)
	{
		this.currentStep = step;
	}

	public void setCurrentTurn(Turn turn)
	{
		this.currentTurn = turn;
	}

	/**
	 * Tells this game state who has priority.
	 * 
	 * @param playerWithPriority The player who has priority.
	 */
	public void setPlayerWithPriority(Player playerWithPriority)
	{
		if(playerWithPriority == null)
			this.playerWithPriorityID = -1;
		else
			this.playerWithPriorityID = playerWithPriority.ID;
	}

	public void setPreviousStep(Step step)
	{
		this.previousStep = step;
	}

	public Zone stack()
	{
		return this.get(this.stackID);
	}
}
