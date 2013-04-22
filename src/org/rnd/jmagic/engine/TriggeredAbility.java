package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/**
 * Represents a triggered ability.
 * 
 * ONLY {@link EventTriggeredAbility} and {@link StateTriggeredAbility} get to
 * extend this. If you're writing a new triggered ability, extend one of those.
 */
public abstract class TriggeredAbility extends NonStaticAbility
{
	/**
	 * This ability can trigger if {@link #canTrigger} evaluates to nonempty.
	 */
	public SetGenerator canTrigger;

	/**
	 * 603.4. A triggered ability may read "When/Whenever/At [trigger event], if
	 * [condition], [effect]." When the trigger event occurs, the ability checks
	 * whether the stated condition is true. The ability triggers only if it is;
	 * otherwise it does nothing. If the ability triggers, it checks the stated
	 * condition again as it resolves. If the condition isn't true at that time,
	 * the ability is removed from the stack and does nothing. Note that this
	 * mirrors the check for legal targets. This rule is referred to as the
	 * "intervening 'if' clause" rule. (The word "if" has only its normal
	 * English meaning anywhere else in the text of a card; this rule only
	 * applies to an "if" that immediately follows a trigger condition.)
	 * <p>
	 * This set generator represents a condition contained in the intervening if
	 * clause of a triggered ability. The condition is considered to be met if
	 * the set this generator evaluates to is nonempty.
	 */
	public SetGenerator interveningIf;

	/**
	 * Constructs a triggered ability that does nothing.
	 * 
	 * @param state The game state this ability exists in.
	 * @param abilityText The text of this ability.
	 */
	TriggeredAbility(GameState state, String abilityText)
	{
		super(state, abilityText);

		this.canTrigger = Intersect.instance(AbilitySource.instance(This.instance()), Permanents.instance());

		SetGenerator source = AbilitySource.instance(This.instance());
		SetGenerator isEmblem = EmblemFilter.instance(source);
		SetGenerator inCommandZone = InZone.instance(CommandZone.instance());
		SetGenerator isEmblemInCommandZone = Intersect.instance(isEmblem, inCommandZone);
		SetGenerator permanentsAndPlayers = Union.instance(Permanents.instance(), Players.instance());
		this.canTrigger = Union.instance(Intersect.instance(permanentsAndPlayers, source), isEmblemInCommandZone);

		// Most triggered abilities have no intervening if clauses, so make a
		// set generator that evaluates to non-empty (aka true) by default.
		this.interveningIf = NonEmpty.instance();
	}

	/**
	 * Determines whether this ability can go on the stack. A triggered ability
	 * can't go on the stack if targets for it can't be chosen (or, if it's
	 * modal, if at least one of its modes can't be chosen).
	 * 
	 * @return Whether this ability can go on the stack.
	 */
	public boolean canStack()
	{
		int numValidModes = 0;
		// 603.3c ... If one of the modes would be illegal (due to an inability
		// to choose legal targets, for example), that mode can't be chosen. ...
		for(Mode mode: this.getModes())
			if(mode.canBeChosen(this.game, this))
				numValidModes++;
		if(this.getModes().isEmpty())
			System.err.println("\"" + this + "\" didn't trigger because it has no effects!");
		Integer minimum = Minimum.get(this.getNumModes());
		return minimum == null || numValidModes >= minimum;
	}

	/**
	 * Determines whether this ability can currently trigger. By default, an
	 * ability can trigger if the object it's printed on or granted to is on the
	 * battlefield. Other triggered abilities may override this function for
	 * abilities that can trigger from other zones or under other conditions, or
	 * to restrict the conditions under which it can trigger.
	 * 
	 * @return Whether this ability can trigger.
	 */
	protected final boolean canTrigger()
	{
		return this.canTrigger(this.game.actualState);
	}

	protected boolean canTrigger(GameState state)
	{
		// if(!state.containsIdentified(this.ID))
		// return false;
		if(this.isDelayed())
			return true;
		return !this.canTrigger.evaluate(state, this).isEmpty();
	}

	/** Java-copies this trigger. */
	@Override
	public TriggeredAbility clone(GameState state)
	{
		return (TriggeredAbility)super.clone(state);
	}

	/**
	 * This method is intended to be overridden by derived classes.
	 * 
	 * @return Whether or not this triggered ability is a delayed triggered
	 * ability.
	 */
	public boolean isDelayed()
	{
		return false;
	}

	/** @return True. */
	@Override
	public boolean isTriggeredAbility()
	{
		return true;
	}

	/**
	 * Puts this ability on the stack.
	 * 
	 * @return This ability -- this instance of this ability is already a copy
	 * of one that existed on an object. It was instantiated when the ability
	 * triggered and was put into the waiting trigger list.
	 */
	@Override
	public TriggeredAbility putOnStack(Player controller, Class<? extends Characteristics> faceDownValues)
	{
		if(null != faceDownValues)
			throw new UnsupportedOperationException("Trying to put a triggered ability on the stack face down.");

		GameObject physicalAbility = this.getPhysical();
		this.game.physicalState.waitingTriggers.get(this.controllerID).remove(physicalAbility);
		this.game.physicalState.stack().addToTop(physicalAbility);

		this.game.actualState.waitingTriggers.get(this.controllerID).remove(this);
		this.game.actualState.stack().addToTop(this);

		// select modes...
		Integer minimum = Minimum.get(this.getNumModes());
		if(minimum == null || this.getModes().size() > minimum)
		{
			this.setSelectedModes(this.selectModes());
			physicalAbility.setSelectedModes(new java.util.LinkedList<Mode>(this.getSelectedModes()));
		}
		// ...or choose all of them
		else
			for(GameObject o: this.andPhysical())
				for(Mode mode: this.getModes())
					o.getSelectedModes().add(mode);

		// select targets
		this.selectTargets();
		for(Mode mode: this.getModes())
		{
			if(!this.getSelectedModes().contains(mode))
				continue;
			for(Target possible: mode.targets)
			{
				java.util.List<Target> chosenTargets = new java.util.LinkedList<Target>(this.getChosenTargets().get(possible));
				physicalAbility.getChosenTargets().put(possible, chosenTargets);
				for(Target chosen: chosenTargets)
					if(null == chosen)
						return null;
			}
		}

		for(int i = 1; i <= this.getModes().size(); i++)
		{
			Mode mode = this.getMode(i);
			if(!this.getSelectedModes().contains(mode))
				continue;

			Set division = mode.division.evaluate(this.game, this);
			int divisionAmount = division.getOne(Integer.class);
			if(divisionAmount != 0)
			{
				java.util.LinkedList<Target> targets = new java.util.LinkedList<Target>();
				for(Target target: physicalAbility.getMode(i).targets)
					targets.addAll(physicalAbility.getChosenTargets().get(target));
				controller.divide(divisionAmount, 1, this.ID, division.getOne(String.class), targets);
			}
		}

		Event playEvent = new Event(this.game.physicalState, "'" + this + "' triggered.", EventType.BECOMES_PLAYED);
		playEvent.parameters.put(EventType.Parameter.OBJECT, IdentifiedWithID.instance(this.ID));
		playEvent.perform(null, true);

		return this;
	}

	/** Carries out the instructions for this ability. */
	@Override
	public void followInstructions()
	{
		if(this.interveningIf.evaluate(this.game, this).isEmpty())
			return;

		super.followInstructions();
	}

	/**
	 * Triggers this ability and returns the instance of the ability created by
	 * triggering it.
	 * 
	 * @return The instance of the ability created by triggering it.
	 */
	protected TriggeredAbility triggerAndReturnNewInstance()
	{
		return this.triggerAndReturnNewInstance(this.game.actualState);
	}

	/**
	 * Triggers this ability from a game state other than the current one (for
	 * look back in time triggers) and returns the instance of the ability
	 * created by triggering it.
	 * 
	 * @param triggerFrom The game state in which the trigger is assumed to
	 * exist.
	 * @return The instance of the ability created by triggering it.
	 */
	protected TriggeredAbility triggerAndReturnNewInstance(GameState triggerFrom)
	{
		if(this.interveningIf.evaluate(triggerFrom, this).isEmpty())
			return null;

		Identified source = this.getSource(triggerFrom);
		int controller;
		if(source.isGameObject())
			controller = ((GameObject)source).getController(triggerFrom).ID;
		else
			controller = source.ID;

		TriggeredAbility newAbility = (TriggeredAbility)this.create(this.game);
		newAbility.sourceID = this.sourceID;
		newAbility.controllerID = controller;
		newAbility.linkManager = this.linkManager.clone();
		if(source.isGameObject())
			newAbility.textChanges.addAll(((GameObject)source).textChanges);

		this.game.physicalState.waitingTriggers.get(controller).add(newAbility);
		newAbility.printedVersionID = this.ID;
		newAbility.grantedByID = this.grantedByID;
		return newAbility;
	}

	/**
	 * Tells this triggered ability that it can only trigger while its source is
	 * in a graveyard (rather than on the battlefield).
	 */
	public void triggersFromGraveyard()
	{
		SetGenerator thisCard = AbilitySource.instance(This.instance());
		SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
		this.canTrigger = Intersect.instance(thisCard, inGraveyards);
	}

	/**
	 * Tells this triggered ability that it can only trigger while its source is
	 * in a hand (rather than on the battlefield).
	 */
	public void triggersFromHand()
	{
		SetGenerator thisCard = AbilitySource.instance(This.instance());
		SetGenerator inHands = InZone.instance(HandOf.instance(Players.instance()));
		this.canTrigger = Intersect.instance(inHands, thisCard);
	}

	/**
	 * Tells this triggered ability that it can only trigger while its source is
	 * on the stack (rather than on the battlefield).
	 */
	public void triggersFromStack()
	{
		SetGenerator thisCard = AbilitySource.instance(This.instance());
		SetGenerator onStack = InZone.instance(Stack.instance());
		this.canTrigger = Intersect.instance(thisCard, onStack);
	}
}
