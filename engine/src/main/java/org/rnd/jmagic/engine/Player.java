package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.PlayerInterface.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.sanitized.*;

/** Represents someone playing the game. */
public final class Player extends Identified implements AttachableTo, Attackable, Sanitizable, Targetable, CanHaveAbilities
{
	public java.util.Collection<Integer> attachments;

	private MultipleSetPattern cantBeTheTargetOf;
	private MultipleSetPattern cantBeAttachedBy;

	/** The interface through which this player communicates with the game. */
	public PlayerInterface comm;

	public java.util.List<Counter> counters;

	/** The creatures currently attacking this player. */
	public java.util.List<Integer> defendingIDs;

	private Integer maxHandSize;

	private int graveyardID;
	private int handID;
	private int libraryID;
	public int lifeTotal;
	public Integer totalLandActions;
	/**
	 * If damage would reduce your life total to less than [x], it reduces it to
	 * [x] instead.
	 */
	public Integer minimumLifeTotalFromDamage;
	/**
	 * keys are source object IDs, values are mulligan options (i.e. Serum
	 * Powder)
	 */
	public java.util.Map<Integer, EventFactory> mulliganOptions;
	public boolean outOfGame;
	public ManaPool pool;
	private int sideboardID;

	private java.util.Collection<Keyword> keywordAbilities;
	private java.util.Collection<StaticAbility> staticAbilities;
	private java.util.Collection<NonStaticAbility> nonStaticAbilities;
	private final SetGenerator thisPlayer;

	/**
	 * True if the player has failed to draw a card since the most recent time
	 * SBAs were checked.
	 */
	public boolean unableToDraw;

	public boolean wonGame;

	/**
	 * @param state The game state this player exists in.
	 * @param name The player's name.
	 * @param comm The interface this player uses to communicate with the game.
	 */
	Player(GameState state, String name, PlayerInterface comm)
	{
		super(state);

		state.ensureTracker(new NormalMulliganTracker());

		this.setName(name);

		this.attachments = new java.util.LinkedList<Integer>();
		this.cantBeTheTargetOf = null;
		this.cantBeAttachedBy = null;
		this.comm = comm;
		this.counters = new java.util.LinkedList<Counter>();
		this.defendingIDs = new java.util.LinkedList<Integer>();
		this.maxHandSize = 7;
		this.graveyardID = new Zone(state, name + "'s Graveyard").ID;
		this.handID = new Zone(state, name + "'s Hand").ID;
		this.libraryID = new Zone(state, name + "'s Library").ID;
		this.lifeTotal = 0;
		this.minimumLifeTotalFromDamage = null;
		this.mulliganOptions = new java.util.HashMap<Integer, EventFactory>();
		this.outOfGame = false;
		this.pool = new ManaPool();
		this.sideboardID = new Zone(state, name + "'s Sideboard").ID;
		this.keywordAbilities = new java.util.LinkedList<Keyword>();
		this.nonStaticAbilities = new java.util.LinkedList<NonStaticAbility>();
		this.staticAbilities = new java.util.LinkedList<StaticAbility>();
		this.thisPlayer = org.rnd.jmagic.engine.generators.PlayerByID.instance(this.ID);
		this.totalLandActions = 1;
		this.unableToDraw = false;
		this.wonGame = false;
	}

	/**
	 * @param ability The keyword ability to add.
	 */
	public final boolean addAbility(Keyword ability)
	{
		// Do this on two lines to prevent short-circuiting skipping an
		// evaluation
		boolean ret = this.keywordAbilities.add(ability);
		ret = ability.apply(this) && ret;
		return ret;
	}

	/**
	 * @param ability The static ability to add.
	 */
	public final boolean addAbility(NonStaticAbility ability)
	{
		ability.sourceID = this.ID;
		return this.nonStaticAbilities.add(ability);
	}

	/**
	 * @param ability The static ability to add.
	 */
	public final boolean addAbility(StaticAbility ability)
	{
		ability.sourceID = this.ID;
		return this.staticAbilities.add(ability);
	}

	@Override
	public void addAttachment(int attachment)
	{
		this.attachments.add(attachment);
	}

	@Override
	public void addAttacker(int attackingID)
	{
		this.defendingIDs.add(attackingID);
	}

	public void alert(GameState state, int... ensurePresent)
	{
		if(null != state)
		{
			if(this.state != this.game.physicalState)
				this.getPhysical().alert(state, ensurePresent);
			else
				this.comm.alertState(new org.rnd.jmagic.sanitized.SanitizedGameState(state, this, ensurePresent));
		}
	}

	public void alert(SanitizedEvent event)
	{
		this.comm.alertEvent(event);
	}

	@Override
	public boolean attackable()
	{
		return true;
	}

	/**
	 * @return A pattern describing all the kinds of things that can't be
	 * attached to this player.
	 */
	@Override
	public final SetPattern cantBeAttachedBy()
	{
		return this.cantBeAttachedBy == null ? SetPattern.NEVER_MATCH : this.cantBeAttachedBy;
	}

	/**
	 * Tells this object it can't be attached by some kinds of objects.
	 * 
	 * @param restriction SetPattern describing the kind of object that can't
	 * attach to this.
	 */
	@Override
	public final void cantBeAttachedBy(SetPattern restriction)
	{
		if(this.cantBeAttachedBy == null)
			this.cantBeAttachedBy = new MultipleSetPattern(false);
		this.cantBeAttachedBy.addPattern(restriction);
	}

	/**
	 * @return A pattern describing all the kinds of things this player can't be
	 * the target of.
	 */
	@Override
	public final SetPattern cantBeTheTargetOf()
	{
		return this.cantBeTheTargetOf == null ? SetPattern.NEVER_MATCH : this.cantBeTheTargetOf;
	}

	/**
	 * Tells this object it can't be the target of some kind of spell or
	 * ability.
	 * 
	 * @param what The kind of spell or ability this object can't be the target
	 * of.
	 */
	@Override
	public final void cantBeTheTargetOf(SetPattern what)
	{
		if(this.cantBeTheTargetOf == null)
			this.cantBeTheTargetOf = new MultipleSetPattern(false);
		this.cantBeTheTargetOf.addPattern(what);
	}

	/**
	 * Causes this player to make a choice.
	 * 
	 * @param <T> The kinds of things the player is choosing from.
	 * @param chooseParameters Variables affecting the choice, including the
	 * possible choices, the lowerbound/upperbound, and the choice type.
	 * @return The chosen T's.
	 */
	public <T extends java.io.Serializable> java.util.List<T> choose(ChooseParameters<T> chooseParameters)
	{
		if(this.game.actualState.controlledPlayers.containsKey(this.ID))
		{
			int controllerID = this.game.actualState.controlledPlayers.get(this.ID);
			if(controllerID != this.ID)
				return this.game.actualState.<Player>get(controllerID).choose(chooseParameters);
		}

		// if(Game.debugging)
		// System.out.println(this + " is being asked to choose between " +
		// chooseParameters.lowerBound + " and " + chooseParameters.upperBound +
		// " from " + chooseParameters.choices);

		int maximumChoices = chooseParameters.choices.size();
		if(maximumChoices < Minimum.get(chooseParameters.number))
			chooseParameters.number = new Set(maximumChoices);
		else if(!chooseParameters.allowMultiples)
			chooseParameters.number = Intersect.get(chooseParameters.number, new Set(new org.rnd.util.NumberRange(0, maximumChoices)));

		// This if-block is very important, because during these events, the
		// objects have been moved, and are likely not visible to the player
		// anymore. Thus, the objects (and their abilities) must be ensured to
		// be available to the player.
		if(chooseParameters.type == PlayerInterface.ChoiceType.MOVEMENT_LIBRARY || chooseParameters.type == PlayerInterface.ChoiceType.MOVEMENT_GRAVEYARD)
			for(T choice: chooseParameters.choices)
			{
				SanitizedGameObject object = (SanitizedGameObject)choice;
				chooseParameters.ensurePresent.addAll(object.sanitizeAbilities(this.state, this));
			}
		else
		{
			for(Player p: this.game.actualState.players)
				if(chooseParameters.thisID != -1)
					p.alert(this.game.actualState, chooseParameters.thisID);
				else
					p.alert(this.game.actualState);
		}

		boolean choicesValid = false;
		java.util.List<Integer> chosen = null;
		java.util.List<T> ret = null;
		while(!choicesValid)
		{
			ret = new java.util.LinkedList<T>();
			choicesValid = true;

			for(Player p: this.game.actualState.players)
				if(!p.equals(this))
					p.comm.alertWaiting(this.sanitize(this.game.actualState, p));
			chosen = this.comm.choose(chooseParameters);
			for(Player p: this.game.actualState.players)
				p.comm.alertWaiting(null);

			// Make sure the right number of choices were chosen
			if(Intersect.get(chooseParameters.number, new Set(chosen.size())).isEmpty())
			{
				choicesValid = false;
				continue;
			}

			// Make sure each choice was chosen only once (or that multiples are
			// allowed) and translate the indices to objects
			for(Integer i: chosen)
			{
				if(chooseParameters.allowMultiples || chosen.indexOf(i) == chosen.lastIndexOf(i))
					ret.add(chooseParameters.choices.get(i));
				else
				{
					choicesValid = false;
					break;
				}
			}
		}

		ChooseParameters<T> choiceMade = new ChooseParameters<T>(chooseParameters.number, ret, chooseParameters.type, chooseParameters.reason);
		choiceMade.thisID = chooseParameters.thisID;
		choiceMade.replacement = chooseParameters.replacement;

		if(chooseParameters.reason.isPublic)
			for(Player p: this.game.actualState.players)
				p.comm.alertChoice(this.ID, choiceMade);
		else
			this.comm.alertChoice(this.ID, choiceMade);

		return ret;
	}

	public <T extends java.io.Serializable> java.util.List<T> choose(int lowerBound, int upperBound, java.util.Collection<T> choices, PlayerInterface.ChoiceType type, ChooseReason description)
	{
		return this.choose(new PlayerInterface.ChooseParameters<T>(lowerBound, upperBound, new java.util.LinkedList<T>(choices), type, description));
	}

	/**
	 * Causes this player to make a choice.
	 * 
	 * @param <T> The kinds of things the player is choosing from.
	 * @param number The number of choices the player must make.
	 * @param choices The legal choices.
	 * @param type What kind of choice this is.
	 * @return The chosen T's
	 */
	public <T extends java.io.Serializable> java.util.List<T> choose(int number, java.util.Collection<T> choices, PlayerInterface.ChoiceType type, ChooseReason description)
	{
		return this.choose(number, number, choices, type, description);
	}

	/**
	 * Convenience method that causes this player to choose any one of the five
	 * colors, using the CHOOSE_COLOR choose reason.
	 * 
	 * @return The chosen color.
	 */
	public Color chooseColor(int whatForID)
	{
		return this.chooseColor(Color.allColors(), whatForID);
	}

	/**
	 * Convenience method that causes this player to choose a color from a
	 * specific set of colors, using the CHOOSE_COLOR choose reason.
	 * 
	 * @return The chosen color.
	 */
	public Color chooseColor(java.util.Collection<Color> choices, int whatForID)
	{
		PlayerInterface.ChooseParameters<Color> chooseParameters = new PlayerInterface.ChooseParameters<Color>(1, 1, new java.util.LinkedList<Color>(choices), PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR);
		chooseParameters.thisID = whatForID;
		return this.choose(chooseParameters).iterator().next();
	}

	/**
	 * Causes the player to choose a number.
	 * 
	 * @param range The legal choices.
	 * @param description An explanation of what the player is choosing.
	 * @return The chosen number.
	 */
	public int chooseNumber(org.rnd.util.NumberRange range, String description)
	{
		if(this.game.actualState.controlledPlayers.containsKey(this.ID))
		{
			int controllerID = this.game.actualState.controlledPlayers.get(this.ID);
			if(controllerID != this.ID)
				return this.game.actualState.<Player>get(controllerID).chooseNumber(range, description);
		}

		int choice = 0;
		boolean valid = false;
		while(!valid)
		{
			choice = this.comm.chooseNumber(range, description + " " + range);
			valid = range.contains(choice);
		}
		return choice;
	}

	@Override
	public Player clone(GameState state)
	{
		Player ret = (Player)super.clone(state);
		ret.attachments = new java.util.LinkedList<Integer>(this.attachments);
		ret.counters = new java.util.LinkedList<Counter>(this.counters);
		ret.defendingIDs = new java.util.LinkedList<Integer>(ret.defendingIDs);

		ret.pool = new ManaPool(this.pool);

		// these should not be preserved between backups since it's
		// re-computed every time the game state is refreshed.
		ret.cantBeTheTargetOf = null;
		ret.cantBeAttachedBy = null;

		// players don't natively have abilities, so we can just make empty
		// lists on clone (abilities will be regenerated when the game state is
		// refreshed)
		ret.keywordAbilities = new java.util.LinkedList<Keyword>();
		ret.nonStaticAbilities = new java.util.LinkedList<NonStaticAbility>();
		ret.staticAbilities = new java.util.LinkedList<StaticAbility>();

		// mulligan options are only produced by effects
		ret.mulliganOptions = new java.util.HashMap<Integer, EventFactory>();

		return ret;
	}

	/** @return The number of poison counters this player has. */
	public int countPoisonCounters()
	{
		int count = 0;
		for(Counter c: this.counters)
			if(Counter.CounterType.POISON == c.getType())
				++count;
		return count;
	}

	/**
	 * Causes this player to divide a quantity across a collection of targets.
	 * 
	 * @param quantity The quantity to divide.
	 * @param minimum The minimum each target must receive.
	 * @param whatFrom The thing causing a division (creature in combat, spell
	 * distributing damage or counters, etc)
	 * @param targets The targets across which the player must divide. When this
	 * function is finished, each target's division member will contain the
	 * player's division for that target.
	 */
	public void divide(int quantity, int minimum, int whatFrom, String beingDivided, java.util.List<Target> targets)
	{
		if(this.game.actualState.controlledPlayers.containsKey(this.ID))
		{
			int controllerID = this.game.actualState.controlledPlayers.get(this.ID);
			if(controllerID != this.ID)
			{
				this.game.actualState.<Player>get(controllerID).divide(quantity, minimum, whatFrom, beingDivided, targets);
				return;
			}
		}

		java.util.List<SanitizedTarget> sanitizedTargets = new java.util.LinkedList<SanitizedTarget>();
		for(Target target: targets)
		{
			sanitizedTargets.add(new SanitizedTarget(target));
		}

		boolean divisionValid = false;
		while(!divisionValid)
		{
			this.comm.divide(quantity, minimum, whatFrom, beingDivided, sanitizedTargets);

			divisionValid = true;

			int totalDivision = 0;
			for(SanitizedTarget target: sanitizedTargets)
			{
				if(target.division < minimum)
				{
					divisionValid = false;
					break;
				}
				totalDivision += target.division;
			}
			if(totalDivision != quantity)
				divisionValid = false;
		}

		for(int i = 0; i < targets.size(); ++i)
		{
			Target t = targets.get(i);
			SanitizedTarget st = sanitizedTargets.get(i);
			t.targetID = st.targetID;
			t.division = st.division;
		}
	}

	@Override
	public Player getActual()
	{
		return (Player)super.getActual();
	}

	@Override
	public java.util.Collection<Integer> getAttachments()
	{
		return new java.util.LinkedList<Integer>(this.attachments);
	}

	/** @return This player's graveyard */
	public Zone getGraveyard(GameState state)
	{
		return state.get(this.graveyardID);
	}

	public int getGraveyardID()
	{
		return this.graveyardID;
	}

	@Override
	public java.util.Collection<Keyword> getKeywordAbilities()
	{
		return java.util.Collections.unmodifiableCollection(this.keywordAbilities);
	}

	/** @return This player's hand */
	public Zone getHand(GameState state)
	{
		return state.get(this.handID);
	}

	public int getHandID()
	{
		return this.handID;
	}

	/** @return This player's library */
	public Zone getLibrary(GameState state)
	{
		return state.get(this.libraryID);
	}

	public int getLibraryID()
	{
		return this.libraryID;
	}

	public Integer getMaxHandSize()
	{
		return this.maxHandSize;
	}

	@Override
	public java.util.Collection<NonStaticAbility> getNonStaticAbilities()
	{
		return java.util.Collections.unmodifiableCollection(this.nonStaticAbilities);
	}

	/** @return This player in the physical game state. */
	@Override
	public Player getPhysical()
	{
		return (Player)super.getPhysical();
	}

	/** @return This player's sideboard */
	public Zone getSideboard(GameState state)
	{
		return state.get(this.sideboardID);
	}

	public int getSideboardID()
	{
		return this.sideboardID;
	}

	@Override
	public java.util.Collection<StaticAbility> getStaticAbilities()
	{
		return java.util.Collections.unmodifiableCollection(this.staticAbilities);
	}

	/** @return Whether this player has the specified ability. */
	public final boolean hasAbility(Class<? extends Keyword> ability)
	{
		for(Keyword k: this.keywordAbilities)
			if(ability.isAssignableFrom(k.getClass()))
				return true;

		return false;
	}

	/** @return True. */
	@Override
	public boolean isPlayer()
	{
		return true;
	}

	/** Tells this player they may activate mana abilities. */
	public void mayActivateManaAbilities()
	{
		// construct a dynamic state in which the player may only activate mana
		// abilities, making sure to preserve the existing acting player in case
		// it is different or doesn't exist
		this.game.refreshActualState();

		// loop until the player chooses not to activate any more mana abilties
		while(true)
		{
			Player thisPlayer = this.getActual();
			this.game.generatePlayerActions(thisPlayer, true);
			java.util.List<PlayerAction> actions = thisPlayer.sanitizeAndChoose(this.game.actualState, 0, 1, this.game.actualState.playerActions, PlayerInterface.ChoiceType.ACTIVATE_MANA_ABILITIES, PlayerInterface.ChooseReason.ACTIVATE_MANA_ABILITIES);
			if(0 == actions.size())
				break;
			if(!actions.get(0).saveStateAndPerform())
				this.game.refreshActualState();
		}
	}

	public void modifyMaxHandSize(int change)
	{
		// if the upper limit isn't infinity, modify it
		if(this.maxHandSize != null)
			setMaxHandSize(this.maxHandSize + change);
	}

	/**
	 * @param ability The keyword ability to remove.
	 */
	public final boolean removeAbility(Keyword ability)
	{
		// do this on two lines to prevent short circuiting
		boolean ret = this.keywordAbilities.remove(ability);
		ret = ability.remove(this) && ret;
		return ret;
	}

	/**
	 * @param ability The static ability to remove.
	 */
	public final boolean removeAbility(StaticAbility ability)
	{
		return this.staticAbilities.remove(ability);
	}

	@Override
	public void removeAttachment(int attachment)
	{
		this.attachments.remove(attachment);
	}

	@Override
	public void removeAttacker(int attackingID)
	{
		this.defendingIDs.remove((Integer)attackingID);
	}

	@Override
	public SanitizedPlayer sanitize(GameState state, Player whoFor)
	{
		// TODO : return new SanitizedPlayer(state.<Player>get(this.ID));
		// or do a check to make sure we're sanitizing into the same state this
		// is from
		return new SanitizedPlayer(state.<Player>get(this.ID));
	}

	public <T> java.util.List<T> sanitizeAndChoose(GameState state, int low, Integer high, java.util.Collection<T> choices, PlayerInterface.ChoiceType type, ChooseReason description)
	{
		return this.sanitizeAndChoose(state, choices, new ChooseParameters<java.io.Serializable>(low, high, type, description));
	}

	public <T> java.util.List<T> sanitizeAndChoose(GameState state, int number, java.util.Collection<T> choices, PlayerInterface.ChoiceType type, ChooseReason description)
	{
		return this.sanitizeAndChoose(state, number, number, choices, type, description);
	}

	public <T> java.util.List<T> sanitizeAndChoose(GameState state, java.util.Collection<T> choices, ChooseParameters<java.io.Serializable> chooseParameters)
	{
		if(null == this.state)
			throw new IllegalStateException("Tried to have a player in an old state make a choice.");

		// Use a Linked Hash Map here so that iterators over the map return
		// elements in a predictable order (insertion order).
		java.util.Map<java.io.Serializable, T> sanitizedChoices = new java.util.LinkedHashMap<java.io.Serializable, T>();

		for(T choice: choices)
			sanitizedChoices.put(serializable(state, choice), choice);

		chooseParameters.choices.addAll(sanitizedChoices.keySet());
		java.util.List<java.io.Serializable> result = this.choose(chooseParameters);
		java.util.List<T> dirtyResult = new java.util.LinkedList<T>();

		for(java.io.Serializable choice: result)
			dirtyResult.add(sanitizedChoices.get(choice));

		return dirtyResult;
	}

	private java.io.Serializable serializable(GameState state, Object o)
	{
		if(o instanceof Sanitizable)
		{
			if(o instanceof java.io.Serializable)
				throw new UnsupportedOperationException(o + " is both Sanitizable and Serializable");
			return ((Sanitizable)o).sanitize(state, this);
		}
		else if(o instanceof Class<?>)
		{
			return ((Class<?>)o).getAnnotation(Name.class).value();
		}
		else if(o instanceof java.io.Serializable)
		{
			return (java.io.Serializable)o;
		}
		else
		{
			throw new UnsupportedOperationException(o + " is not Sanitizable or Serializable");
		}
	}

	/**
	 * Causes this player to separate some objects into a number of piles.
	 * 
	 * @param numPiles How many piles to separate the objects into. If this is
	 * less than 2, this player will not be asked to make a choice.
	 * @param objects The objects to separate into piles. Non-{@link GameObject}
	 * s in the set will be ignored.
	 * @return If <code>numPiles</code> is less than 1, an empty collection; if
	 * it's 1, a collection containing <code>object</code>; for any other
	 * number, a collection of {@link Set}s where each {@link Set} in the
	 * collection is a pile.
	 */
	public java.util.Collection<Pile> separateIntoPiles(int numPiles, Set objects)
	{
		if(numPiles < 1)
			return java.util.Collections.emptySet();
		if(numPiles == 1)
		{
			Pile pile = new Pile();
			pile.addAll(objects.getAll(GameObject.class));
			return java.util.Collections.singleton(pile);
		}

		java.util.Collection<Pile> ret = new java.util.LinkedList<Pile>();
		java.util.List<GameObject> objectsInNewPile = this.sanitizeAndChoose(this.game.actualState, 0, objects.size(), objects.getAll(GameObject.class), ChoiceType.OBJECTS, ChooseReason.MAKE_PILE);

		Pile newPile = new Pile();
		newPile.addAll(objectsInNewPile);
		ret.add(newPile);

		Set theRest = Set.fromCollection(objects);
		theRest.removeAll(objectsInNewPile);
		ret.addAll(this.separateIntoPiles(numPiles - 1, theRest));
		return ret;
	}

	public void setMaxHandSize(Integer max)
	{
		this.maxHandSize = max;
	}

	/**
	 * @return A set generator that evaluates to the most recent version of this
	 * player in the actual game state.
	 */
	public SetGenerator thisPlayer()
	{
		return this.thisPlayer;
	}
}
