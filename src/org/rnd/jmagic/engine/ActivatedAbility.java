package org.rnd.jmagic.engine;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * Represents an activated ability. An activated ability is an ability that is
 * worded [cost]: [effect]. The default permissions are:
 * <ul>
 * <li>An ability may not be activated if it includes a tap or untap symbol in
 * its cost and the source of this ability has summoning sickness but not
 * {@link org.rnd.jmagic.abilities.keywords.Haste Haste}.</li>
 * <li>An ability may be activated by the controller of its source.</li>
 * <li>An ability may be activated if its source is an object on the
 * battlefield, a player, or an emblem.</li>
 * <li>An ability may be activated if you have priority or its a mana ability
 * (The text "Activate this ability only during X" is actually a restriction,
 * not a timing permission. The only change in timing permissions (so far) is
 * "Activate this ability only any time you could cast a sorcery." which you
 * should use {@link #activateOnlyAtSorcerySpeed()} for, so there's no other way
 * to change it).</li>
 * </ul>
 */
public abstract class ActivatedAbility extends NonStaticAbility
{
	/** keys are ability IDs, values are activation counts */
	public static class ActivationsThisTurn extends Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.HashMap<Integer, Integer> counts = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.counts);

		@SuppressWarnings("unchecked")
		@Override
		public ActivationsThisTurn clone()
		{
			ActivationsThisTurn ret = (ActivationsThisTurn)super.clone();
			ret.counts = (java.util.HashMap<Integer, Integer>)this.counts.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.counts);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type != EventType.CAST_SPELL_OR_ACTIVATE_ABILITY)
				return false;

			Set object = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null);
			if(object.isEmpty())
				return false;
			Object played = object.iterator().next();
			return played instanceof ActivatedAbility;
		}

		@Override
		protected void reset()
		{
			this.counts.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			Object played = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null).iterator().next();
			int key = ((Identified)played).ID;

			if(this.counts.containsKey(key))
				this.counts.put(key, this.counts.get(key) + 1);
			else
				this.counts.put(key, 1);
		}
	}

	private SetGenerator activatePermissionLocation;

	private SetGenerator activatePermissionPlayers;

	private SetGenerator activatePermissionTiming;

	private SetGenerator activateRestriction;

	/** The tap symbol. */
	public boolean costsTap;

	/** The untap symbol. */
	public boolean costsUntap;

	/**
	 * Constructs an activated ability that does nothing.
	 * 
	 * @param state The game state in which the ability exists.
	 * @param abilityText The text of the ability.
	 */
	public ActivatedAbility(GameState state, String abilityText)
	{
		super(state, abilityText);

		if(this.getManaCost() == null)
			this.setManaCost(new ManaPool(""));

		this.costsTap = false;
		this.costsUntap = false;
		this.printedVersionID = this.ID;

		SetGenerator thisCard = AbilitySource.instance(This.instance());
		SetGenerator thisCardIsOnTheBattlefield = Intersect.instance(thisCard, InZone.instance(Battlefield.instance()));
		SetGenerator thisCardIsActuallyAPlayer = Intersect.instance(thisCard, Players.instance());
		SetGenerator thisCardIsActuallyAnEmblemInTheCommandZone = Intersect.instance(EmblemFilter.instance(thisCard), InZone.instance(CommandZone.instance()));
		this.activatePermissionLocation = Union.instance(thisCardIsOnTheBattlefield, thisCardIsActuallyAPlayer, thisCardIsActuallyAnEmblemInTheCommandZone);

		this.activatePermissionPlayers = You.instance();

		// It seems strange to say that this ability can be activated whenever
		// _anyone_ has priority, rather than saying it can be activated by
		// whomever has priority, but actions are only ever generated either
		// when a player has priority or when a player can play mana abilities,
		// so this should be sufficient
		SetGenerator hasPriority = PlayerWithPriority.instance();
		SetGenerator thisIsAManaAbility = ManaAbilityFilter.instance(This.instance());
		this.activatePermissionTiming = Union.instance(hasPriority, thisIsAManaAbility);

		SetGenerator thisHasSummoningSickness = Intersect.instance(HasSummoningSickness.instance(), thisCard);
		SetGenerator thisCostsTapOrUntap = CostsTapOrUntap.instance(This.instance());
		SetGenerator thisHasHaste = Intersect.instance(HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Haste.class), thisCard);
		this.activateRestriction = Both.instance(Not.instance(thisHasHaste), Both.instance(thisCostsTapOrUntap, thisHasSummoningSickness));
	}

	/**
	 * This is only a convenience method to set up the right permissions for
	 * activated abilities that can only be played at sorcery speed (like Equip
	 * or Unearth)
	 */
	protected final void activateOnlyAtSorcerySpeed()
	{
		this.activatePermissionTiming = Intersect.instance(You.instance(), PlayerCanPlaySorcerySpeed.instance());
	}

	protected final void activateOnlyDuringYourUpkeep()
	{
		SetGenerator currentStep = CurrentStep.instance();
		SetGenerator yourUpkeeps = UpkeepStepOf.instance(You.instance());
		this.addActivateRestriction(Not.instance(Intersect.instance(yourUpkeeps, currentStep)));
	}

	/**
	 * Set up the right permissions so that this ability can be activated only
	 * from your graveyard (like Unearth).
	 */
	protected final void activateOnlyFromGraveyard()
	{
		this.activatePermissionLocation = Intersect.instance(ABILITY_SOURCE_OF_THIS, InZone.instance(GraveyardOf.instance(You.instance())));
	}

	/**
	 * This is only a convenience method to set up the right
	 * permissions/restrictions for activated abilities that can only be played
	 * from hand (like Cycling or Reinforce)
	 */
	protected final void activateOnlyFromHand()
	{
		this.activatePermissionLocation = Intersect.instance(ABILITY_SOURCE_OF_THIS, InZone.instance(HandOf.instance(You.instance())));
	}

	/**
	 * Combine a restriction on when this ability may not be activated with the
	 * default value.
	 * 
	 * @param restriction A SetGenerator that evaluates to a non-empty set if
	 * this ability may not be activated.
	 */
	protected final void addActivateRestriction(SetGenerator restriction)
	{
		this.activateRestriction = Union.instance(this.activateRestriction, restriction);
	}

	protected final void anyPlayerMayActivateThisAbility()
	{
		this.activatePermissionPlayers = Players.instance();
	}

	/**
	 * Java-copies this ability for GameState backup purposes.
	 */
	@Override
	public ActivatedAbility clone(GameState state)
	{
		ActivatedAbility ret = (ActivatedAbility)super.clone(state);
		return ret;
	}

	/** @return True. */
	@Override
	public boolean isActivatedAbility()
	{
		return true;
	}

	boolean isActivatableBy(GameState state, Player who)
	{
		if(this.isGhost())
			return false;

		// If an actual-only ability is generated in an earlier state (say, from
		// a land being a Swamp because of Urborg), and the ability is abandoned
		// (say, from the land not being a Swamp anymore), then that ability
		// will have no source when it's cloned into this state. Detect this and
		// don't attempt to offer the player any actions for these abilities.
		if(this.sourceID == -1)
			return false;

		// You can't activate abilities that are on the stack...
		if(this.zoneID != -1)
			return false;

		// If any restrictions apply, skip this ability.
		if(!(this.activateRestriction.evaluate(state, this).isEmpty()))
			return false;

		if(this.activatePermissionLocation.evaluate(state, this).isEmpty())
			return false;

		if(!this.activatePermissionPlayers.evaluate(state, this).contains(who))
			return false;

		if(this.activatePermissionTiming.evaluate(state, this).isEmpty())
			return false;

		return true;
	}

	/**
	 * Determines whether this ability is a mana ability.
	 * 
	 * 605.1a An activated ability without a target that could put mana into a
	 * player's mana pool when it resolves is a mana ability.
	 */
	@Override
	public boolean isManaAbility()
	{
		return !this.hasTargets() && this.addsMana();
	}

	protected final void onlyOpponentsMayActivateThisAbility()
	{
		this.activatePermissionPlayers = OpponentsOf.instance(You.instance());
	}

	/**
	 * Adds a play restriction on this ability that prevents the player from
	 * activating it more than a specified number of times in a turn. This
	 * method ensures the appropriate Flag exists in the game state.
	 * 
	 * @param numPlays The maximum number of times per turn this ability can be
	 * activated.
	 */
	protected final void perTurnLimit(int numPlays)
	{
		this.addActivateRestriction(Intersect.instance(Between.instance(numPlays, null), TimesActivatedThisTurn.instance(This.instance())));

		this.state.ensureTracker(new ActivationsThisTurn());
	}

	/**
	 * Puts an instance of this ability on the stack if it's not a mana ability.
	 * The ability's controller will be the player currently taking an action.
	 * 
	 * @return The instance of the ability on the stack, or if it's a mana
	 * ability, the instance of the ability which will resolve and produce mana.
	 */
	@Override
	public final ActivatedAbility putOnStack(Player controller, Class<? extends Characteristics> faceDownValues)
	{
		if(null != faceDownValues)
			throw new UnsupportedOperationException("Trying to put an activated ability on the stack face down.");

		ActivatedAbility ability = (ActivatedAbility)(this.create(this.game));
		Identified source = this.getSource(this.game.actualState);
		if(source.isGameObject())
			ability.textChanges.addAll(((GameObject)source).textChanges);

		if(!this.isManaAbility())
			this.game.physicalState.stack().addToTop(ability);

		ability.printedVersionID = this.ID;
		ability.grantedByID = this.grantedByID;
		ability.sourceID = this.sourceID;
		ability.linkManager = this.linkManager.clone();

		ability.setController(controller);

		this.game.refreshActualState();
		return (ActivatedAbility)ability.getActual();
	}

	@Override
	public org.rnd.jmagic.sanitized.SanitizedActivatedAbility sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedActivatedAbility(state.<ActivatedAbility>get(this.ID), whoFor);
	}

	/**
	 * Set a permission for when this ability may be activated from its current
	 * location.
	 * 
	 * @param permission A {@link SetGenerator} that evaluates to a non-empty
	 * set if this ability may be activated from its current location.
	 */
	protected final void setActivatePermissionLocation(SetGenerator permission)
	{
		this.activatePermissionLocation = permission;
	}

	/**
	 * This is just to promote the visibility of setManaCost so derived classes
	 * can call it
	 */
	@Override
	protected void setManaCost(ManaPool manaCost)
	{
		super.setManaCost(manaCost);
	}

	public final int timesActivatedThisTurn()
	{
		ActivationsThisTurn flag = this.game.actualState.getTracker(ActivationsThisTurn.class);
		java.util.Map<Integer, Integer> map = flag.getValue(this.game.actualState);
		if(map.containsKey(this.printedVersionID))
			return map.get(this.printedVersionID);
		return 0;
	}
}
