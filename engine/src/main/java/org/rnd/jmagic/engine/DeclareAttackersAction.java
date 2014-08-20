package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.abilities.keywords.*;

/**
 * Represents the player action of declaring attackers. Performing this action
 * causes the active player to declare attackers.
 */
public class DeclareAttackersAction extends PlayerAction
{
	private Player activePlayer;

	private SetGenerator activePlayerGenerator;

	/**
	 * The set of declared attackers. This will be null if the action has not
	 * been performed yet. Note that this collection is populated even after an
	 * unsuccessful declaration of attackers.
	 */
	public java.util.Collection<Integer> attackerIDs;

	/**
	 * This number is calculated on creation and should remain constant since
	 * the actual state shouldn't change in ways which would affect attacking
	 * requirements.
	 */
	private int maxAttackingRequirements;

	/**
	 * What event to use as a parent for all generated Event instances.
	 */
	private Event parent;

	/**
	 * The set of creatures that are used to determine the maximum number of
	 * requirements satisfiable and for the active player to choose from when
	 * choosing attackers.
	 */
	private java.util.List<Integer> possibleAttackerIDs;

	private java.util.Collection<AttackingRequirement> requirementsToCheck;

	/**
	 * Constructs a declare attackers action. One instance of this class is safe
	 * to be performed repeatedly as long as the actual state of the game
	 * doesn't change.
	 * 
	 * @param game The game in which the action will be performed.
	 */
	public DeclareAttackersAction(Game game, Player attacking, Event parent)
	{
		super(game, "Declare attackers", attacking, 0);
		this.attackerIDs = null;
		this.parent = parent;

		// 508.1a The active player chooses which creatures that he or she
		// controls, if any, will attack. The chosen creatures must be untapped,
		// and each one must either have haste or have been controlled by the
		// active player continuously since the turn began.
		this.activePlayer = this.game.actualState.currentTurn().getOwner(this.game.actualState);
		this.activePlayerGenerator = Identity.instance(this.activePlayer);
		SetGenerator activePlayerControls = ControlledBy.instance(this.activePlayerGenerator);
		SetGenerator untappedCreatures = Intersect.instance(CreaturePermanents.instance(), Untapped.instance());
		SetGenerator activePlayersUntappedCreatures = Intersect.instance(untappedCreatures, activePlayerControls);
		SetGenerator validAttackers = RelativeComplement.instance(activePlayersUntappedCreatures, HasSummoningSickness.instance());
		this.possibleAttackerIDs = new java.util.LinkedList<Integer>();
		for(GameObject o: validAttackers.evaluate(this.game, null).getAll(GameObject.class))
			this.possibleAttackerIDs.add(o.ID);

		// Check every requirement
		this.requirementsToCheck = new java.util.LinkedList<AttackingRequirement>(this.game.actualState.attackingRequirements);
		java.util.Iterator<AttackingRequirement> i = this.requirementsToCheck.iterator();
		while(i.hasNext())
			if(!i.next().attackingPlayerIs(this.activePlayer, game.actualState))
				i.remove();

		// Determine the maximum possible set of attacking requirements that can
		// be satisfied
		this.maxAttackingRequirements = this.determineMaximumRequirements(game.actualState, 0);

		// Put the actual game state back to how we found it
		this.game.refreshActualState();
	}

	/**
	 * Figure out the maximum number of attacking requirements that can be
	 * satisfied by recursively evaluating the number of satisfied requirements
	 * for each creature either attacking or not.
	 */
	private int determineMaximumRequirements(GameState state, int index)
	{
		if(this.possibleAttackerIDs.size() <= index)
		{
			if(this.game.actualState.isAttackingRestrictionViolated())
				return 0;
			return this.satisfiedAttackingRequirements();
		}

		// The default state is this creature not attacking
		int ret = determineMaximumRequirements(state, index + 1);
		if(ret == this.requirementsToCheck.size())
			return ret;

		GameObject attacker = state.getByIDObject(this.possibleAttackerIDs.get(index));
		int previousAttackingID = attacker.getAttackingID();

		SetGenerator opponents = OpponentsOf.instance(Identity.instance(this.activePlayer));
		SetGenerator defenders = Union.instance(opponents, Intersect.instance(HasType.instance(Type.PLANESWALKER), ControlledBy.instance(opponents)));
		for(Attackable defender: defenders.evaluate(state, null).getAll(Attackable.class))
		{
			attacker.setAttackingID(((Identified)defender).ID);
			defender.addAttacker(attacker.ID);

			int requirementsAttacking = determineMaximumRequirements(state, index + 1);

			// Undo the work we did
			attacker.setAttackingID(previousAttackingID);
			defender.removeAttacker(attacker.ID);

			if(ret < requirementsAttacking)
				ret = requirementsAttacking;
		}

		return ret;
	}

	/**
	 * Active player declares attackers.
	 * 
	 * @return Whether or not attackers were legally declared.
	 */
	@Override
	public boolean perform()
	{
		// Start with an empty attackingID list
		this.attackerIDs = new java.util.LinkedList<Integer>();

		// 508.1a The active player chooses which creatures that he or she
		// controls, if any, will attack. The chosen creatures must be untapped,
		// and each one must either have haste or have been controlled by the
		// active player continuously since the turn began.
		java.util.List<GameObject> possibleAttackers = new java.util.LinkedList<GameObject>();
		for(Integer i: this.possibleAttackerIDs)
			possibleAttackers.add(this.game.actualState.<GameObject>get(i));

		Player chooser;
		if(-1 == this.game.actualState.declareAttackersPlayerOverride)
			chooser = this.activePlayer.getActual();
		else
			chooser = this.game.actualState.get(this.game.actualState.declareAttackersPlayerOverride);
		java.util.List<GameObject> attackers = chooser.sanitizeAndChoose(this.game.actualState, 0, possibleAttackers.size(), possibleAttackers, PlayerInterface.ChoiceType.ATTACK, PlayerInterface.ChooseReason.DECLARE_ATTACKERS);

		// 508.1b If the defending player controls any planeswalkers, or the
		// game allows the active player to attack multiple other players, the
		// active player announces which player or planeswalker each of the
		// chosen creatures is attacking.
		SetGenerator opponents = OpponentsOf.instance(this.activePlayerGenerator);
		SetGenerator controlledByOpponents = ControlledBy.instance(opponents);
		SetGenerator planeswalkers = HasType.instance(Type.PLANESWALKER);
		java.util.Set<Identified> attackables = Union.instance(Intersect.instance(planeswalkers, controlledByOpponents), opponents).evaluate(this.game, null).getAll(Identified.class);
		for(GameObject attacker: attackers)
		{
			PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(1, 1, PlayerInterface.ChoiceType.ATTACK_WHAT, PlayerInterface.ChooseReason.DECLARE_ATTACK_DEFENDER);
			chooseParameters.thisID = attacker.ID;
			int defendingID = chooser.sanitizeAndChoose(this.game.actualState, attackables, chooseParameters).get(0).ID;

			attacker.setAttackingID(defendingID);

			GameObject physical = attacker.getPhysical();
			physical.setAttackingID(defendingID);

			if(this.game.actualState.containsIdentified(defendingID))
			{
				Identified defending = this.game.actualState.get(defendingID);
				if(defending.isGameObject())
				{
					GameObject defendingObject = (GameObject)defending;
					if(!defendingObject.getTypes().contains(Type.PLANESWALKER))
						throw new IllegalStateException("A creature is attacking a non-Planeswalker GameObject");
					defendingObject.getDefendingIDs().add(attacker.ID);
					defendingObject.getPhysical().getDefendingIDs().add(attacker.ID);
				}
				else
				{
					Player defendingPlayer = (Player)defending;
					defendingPlayer.addAttacker(attacker.ID);
					defendingPlayer.getPhysical().defendingIDs.add(attacker.ID);
				}
			}
		}

		// 508.1c The active player checks each creature he or she controls to
		// see whether it's affected by any restrictions (effects that say a
		// creature can't attack, or that it can't attack unless some condition
		// is met). If any restrictions are being disobeyed, the declaration of
		// attackers is illegal.
		if(this.game.actualState.isAttackingRestrictionViolated())
			return false;

		// 508.1d The active player checks each creature he or she controls to
		// see whether it's affected by any requirements (effects that say a
		// creature must attack, or that it must attack if some condition is
		// met). If the number of requirements that are being obeyed is fewer
		// than the maximum possible number of requirements that could be obeyed
		// without disobeying any restrictions, the declaration of attackers is
		// illegal. If a creature can't attack unless a player pays a cost, that
		// player is not required to pay that cost, even if attacking with that
		// creature would increase the number of requirements being obeyed.
		int requirementsSatisfied = this.satisfiedAttackingRequirements();
		if(requirementsSatisfied < this.maxAttackingRequirements)
			return false;
		else if(this.maxAttackingRequirements < requirementsSatisfied)
			throw new IllegalStateException(this.maxAttackingRequirements + " requirements maximum; " + requirementsSatisfied + " requirements satisfied");

		// TODO : 508.1e If any of the chosen creatures have banding or a
		// "bands with other" ability, the active player announces which
		// creatures, if any, are banded with which.

		// 508.1f The active player taps the chosen creatures.
		SetGenerator attackersWithoutVigilance = RelativeComplement.instance(Identity.fromCollection(attackers), HasKeywordAbility.instance(Vigilance.class));

		Event tapEvent = new Event(this.game.physicalState, "Tap the creatures chosen to attack.", EventType.TAP_PERMANENTS);
		tapEvent.parameters.put(EventType.Parameter.CAUSE, OwnerOf.instance(CurrentTurn.instance()));
		tapEvent.parameters.put(EventType.Parameter.OBJECT, attackersWithoutVigilance);
		tapEvent.perform(null, true);

		// 508.1g If any of the chosen creatures require paying costs to attack,
		// the active player determines the total cost to attack.
		boolean costsRequireMana = false;
		java.util.Collection<Event> totalCostToAttack = new java.util.LinkedList<Event>();

		for(AttackingCost cost: this.game.actualState.attackingCosts)
		{
			Set thisCost = cost.evaluate(attackers);
			if(thisCost == null)
				continue;

			Event eventCost = thisCost.getOne(Event.class);
			if(eventCost != null)
				totalCostToAttack.add(eventCost);
			else
			{
				java.util.Set<ManaSymbol> manaCost = thisCost.getAll(ManaSymbol.class);
				if(!manaCost.isEmpty())
				{
					Event manaEvent = new Event(this.game.physicalState, "Pay " + manaCost, EventType.PAY_MANA);
					manaEvent.parameters.put(EventType.Parameter.CAUSE, Identity.instance(this.game));
					manaEvent.parameters.put(EventType.Parameter.PLAYER, this.activePlayerGenerator);
					manaEvent.parameters.put(EventType.Parameter.COST, Identity.fromCollection(manaCost));
					totalCostToAttack.add(manaEvent);
					costsRequireMana = true;
				}
			}
		}

		// 508.1h If any of the costs require mana, the active
		// player then has a chance to activate mana abilities
		if(costsRequireMana)
			this.activePlayer.mayActivateManaAbilities();

		// 508.1i Once the player has enough mana in his or her mana pool, he or
		// she pays all costs in any order.
		if(!totalCostToAttack.isEmpty())
		{
			java.util.List<Event> orderedCosts = this.activePlayer.getPhysical().sanitizeAndChoose(this.game.actualState, totalCostToAttack.size(), totalCostToAttack, PlayerInterface.ChoiceType.COSTS, PlayerInterface.ChooseReason.ORDER_ATTACK_COSTS);
			for(Event cost: orderedCosts)
			{
				cost.isEffect = false;
				cost.isCost = true;
				if(!cost.perform(null, true))
					return false;
			}
		}

		// 508.1j Each chosen creature still controlled by the active player
		// becomes an attacking creature.

		// These two loops are seperated so that anything that triggers on
		// DECLARE_ONE_ATTACKER will have all the attacking information.
		for(GameObject attacker: attackers)
			if(attacker.controllerID == this.activePlayer.ID)
				this.attackerIDs.add(attacker.ID);

		for(GameObject attacker: attackers)
		{
			Event declareAttackerEvent = new Event(this.game.physicalState, attacker + " attacks.", EventType.DECLARE_ONE_ATTACKER);
			declareAttackerEvent.parameters.put(EventType.Parameter.DEFENDER, IdentifiedWithID.instance(attacker.getAttackingID()));
			declareAttackerEvent.parameters.put(EventType.Parameter.OBJECT, IdentifiedWithID.instance(attacker.ID));
			declareAttackerEvent.perform(this.parent, true);
		}

		return true;
	}

	private int satisfiedAttackingRequirements()
	{
		int requirementsSatisfied = 0;
		for(AttackingRequirement r: this.requirementsToCheck)
			if(r.isSatisfied(this.game.actualState))
				++requirementsSatisfied;
		return requirementsSatisfied;
	}

	@Override
	public PlayerInterface.ReversionParameters getReversionReason()
	{
		Player player = this.game.physicalState.get(this.actorID);
		return new PlayerInterface.ReversionParameters("DeclareAttackersAction", player.getName() + " declared an illegal attack.");
	}
}
