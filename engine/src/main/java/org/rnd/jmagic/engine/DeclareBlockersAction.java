package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/**
 * Represents the player action of declaring blockers. Performing this action
 * causes one player to declare blockers.
 */
public class DeclareBlockersAction extends PlayerAction
{
	/**
	 * The set of attacking creatures that are used to determine the maximum
	 * number of requirements satisfiable.
	 */
	private java.util.List<Integer> attackerIDs;

	/**
	 * Which player is declaring blockers.
	 */
	private Player defender;

	/**
	 * This number is calculated on creation and should remain constant since
	 * the actual state shouldn't change in ways which would affect attacking
	 * requirements.
	 */
	private int maxBlockingRequirements;

	/**
	 * What event to use as a parent for all generated Event instances.
	 */
	private Event parent;

	/**
	 * The set of blocking creatures that are used to determine the maximum
	 * number of requirements satisfiable and for the active player to choose
	 * from when choosing blockers.
	 */
	private java.util.List<Integer> possibleBlockerIDs;

	private java.util.Collection<BlockingRequirement> requirementsToCheck;

	/**
	 * Constructs a declare blockers action that, when performed, will cause a
	 * specific player to declare blockers.
	 * 
	 * @param game The game in which this action is to be performed.
	 * @param defender The player who is to declare blockers.
	 * @param parent An event of type DECLARE_BLOCKERS that is performing this
	 * action.
	 */
	public DeclareBlockersAction(Game game, Player defender, Event parent)
	{
		super(game, defender + " declares blockers", defender, 0);
		this.defender = defender;
		this.parent = parent;

		// 509.1a The defending player chooses which creatures that he or she
		// controls, if any, will block. The chosen creatures must be untapped.
		SetGenerator defendingPlayerGenerator = Identity.instance(this.defender);
		SetGenerator defendingPlayerControls = ControlledBy.instance(defendingPlayerGenerator);
		SetGenerator untappedCreatures = Intersect.instance(HasType.instance(Type.CREATURE), Untapped.instance());
		SetGenerator defendingPlayersUntappedCreatures = Intersect.instance(untappedCreatures, defendingPlayerControls);
		this.possibleBlockerIDs = new java.util.LinkedList<Integer>();
		for(GameObject o: defendingPlayersUntappedCreatures.evaluate(this.game, null).getAll(GameObject.class))
			this.possibleBlockerIDs.add(o.ID);

		// For each of the chosen creatures, the defending player chooses one
		// creature for it to block that's attacking him, her, or a planeswalker
		// he or she controls.
		SetGenerator planeswalkers = HasType.instance(Type.PLANESWALKER);
		SetGenerator defendingPlayersPlaneswalkers = Intersect.instance(planeswalkers, defendingPlayerControls);
		SetGenerator defendAgainst = Attacking.instance(Union.instance(defendingPlayerGenerator, defendingPlayersPlaneswalkers));
		this.attackerIDs = new java.util.LinkedList<Integer>();
		for(GameObject o: defendAgainst.evaluate(this.game, null).getAll(GameObject.class))
			this.attackerIDs.add(o.ID);

		this.requirementsToCheck = new java.util.LinkedList<BlockingRequirement>(this.game.actualState.blockingRequirements);
		java.util.Iterator<BlockingRequirement> i = this.requirementsToCheck.iterator();
		while(i.hasNext())
			if(!i.next().defendingPlayerIs(defender, game.actualState))
				i.remove();

		// Determine the maximum possible set of blocking requirements that can
		// be satisfied
		this.maxBlockingRequirements = this.determineMaximumRequirements(0, 0, 0);

		// Put the actual game state back to how we found it
		this.game.refreshActualState();
	}

	/**
	 * Figure out the maximum number of blocking requirements that can be
	 * satisfied by recursively evaluating the number of satisfied requirements
	 * for each creature.
	 * 
	 * @param blockerIndex The index of the blocker in this.validBlockers that
	 * is being evaluated (start at 0 to initialize)
	 * @param attackerIndex The index of the attacker in this.attackers that is
	 * being evaluated (start at 0 to initialize)
	 * @param numBlocks How many blocks this blocker has already made (start at
	 * 0 to initialize)
	 */
	private int determineMaximumRequirements(int blockerIndex, int attackerIndex, int numBlocks)
	{
		if(this.possibleBlockerIDs.size() <= blockerIndex)
		{
			// If there are no more blockers to evaluate, return the number of
			// requirements satisfied
			// this.game.refreshActualState();
			if(this.game.actualState.isBlockingRestrictionViolated())
				return 0;
			return this.satisfiedBlockingRequirements();
		}

		if(this.attackerIDs.size() <= attackerIndex)
			// If there are no more attackers to evaluate, start on the next
			// blocker
			return determineMaximumRequirements(blockerIndex + 1, 0, 0);

		int blockerID = this.possibleBlockerIDs.get(blockerIndex);

		// Start the calculation off with not blocking this attacker
		int requirementsNotBlocking = determineMaximumRequirements(blockerIndex, attackerIndex + 1, numBlocks);
		if(requirementsNotBlocking == this.requirementsToCheck.size())
			return requirementsNotBlocking;

		// If this creature could block another attacker, try to
		GameObject blocker = this.game.actualState.getByIDObject(blockerID);
		if(numBlocks < blocker.getMaximumBlocks())
		{
			int attackerID = this.attackerIDs.get(attackerIndex);
			GameObject attacker = this.game.actualState.getByIDObject(attackerID);

			blocker.getBlockingIDs().add(attackerID);
			boolean usedToBeNull = false;
			if(null == attacker.getBlockedByIDs())
			{
				attacker.setBlockedByIDs(new java.util.LinkedList<Integer>());
				usedToBeNull = true;
			}
			attacker.getBlockedByIDs().add(blockerID);

			int requirementsBlocking = determineMaximumRequirements(blockerIndex, attackerIndex + 1, numBlocks + 1);

			// Undo all the work in the physical state
			blocker.getBlockingIDs().remove((Integer)attackerID);
			if(usedToBeNull)
				attacker.setBlockedByIDs(null);
			else
				attacker.getBlockedByIDs().remove((Integer)blockerID);

			if(requirementsNotBlocking < requirementsBlocking)
				return requirementsBlocking;
		}

		return requirementsNotBlocking;
	}

	/**
	 * Defender specified on construction declares blockers.
	 * 
	 * @return True.
	 */
	@Override
	public boolean perform()
	{
		java.util.List<GameObject> attackers = new java.util.LinkedList<GameObject>();
		for(Integer i: this.attackerIDs)
			attackers.add(this.game.actualState.<GameObject>get(i));

		java.util.List<GameObject> possibleBlockers = new java.util.LinkedList<GameObject>();
		for(Integer i: this.possibleBlockerIDs)
			possibleBlockers.add(this.game.actualState.<GameObject>get(i));

		Player chooser;
		if(-1 == this.game.actualState.declareBlockersPlayerOverride)
			chooser = this.defender.getActual();
		else
			chooser = this.game.actualState.get(this.game.actualState.declareBlockersPlayerOverride);
		java.util.List<GameObject> blockers = chooser.sanitizeAndChoose(this.game.actualState, 0, possibleBlockers.size(), possibleBlockers, PlayerInterface.ChoiceType.BLOCK, PlayerInterface.ChooseReason.DECLARE_BLOCKERS);

		for(GameObject attacker: attackers)
		{
			attacker.setBlockedByIDs(null);
			attacker.getPhysical().setBlockedByIDs(null);
		}

		java.util.Set<GameObject> physicalBlockers = new java.util.HashSet<GameObject>();
		for(GameObject blocker: blockers)
		{
			int maximumBlocks = blocker.getMaximumBlocks();
			if(maximumBlocks == -1)
				maximumBlocks = attackers.size();
			PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(1, maximumBlocks, PlayerInterface.ChoiceType.BLOCK_WHAT, PlayerInterface.ChooseReason.DECLARE_BLOCKED_ATTACKER);
			chooseParameters.thisID = blocker.ID;
			java.util.List<GameObject> blockingWhat = chooser.sanitizeAndChoose(this.game.actualState, attackers, chooseParameters);

			java.util.List<GameObject> blockerAndPhysical = blocker.andPhysical();
			GameObject physicalBlocker = blockerAndPhysical.get(blockerAndPhysical.size() - 1);
			physicalBlockers.add(physicalBlocker);

			for(GameObject attacker: blockingWhat)
			{
				java.util.List<GameObject> attackerAndPhysical = attacker.andPhysical();
				if(attackerAndPhysical.get(attackerAndPhysical.size() - 1).getBlockedByIDs() == null)
					for(GameObject o: attackerAndPhysical)
						o.setBlockedByIDs(new java.util.LinkedList<Integer>());
				for(GameObject o: attackerAndPhysical)
					o.getBlockedByIDs().add(blocker.ID);
				for(GameObject o: blockerAndPhysical)
					o.getBlockingIDs().add(attacker.ID);
			}
		}

		// Then he or she determines whether this set of blocks is legal.
		// this.game.refreshActualState();
		if(this.game.actualState.isBlockingRestrictionViolated())
			return false;

		// Make sure as many blocking requirements as possible are satisfied
		int requirementsSatisfied = this.satisfiedBlockingRequirements();
		if(requirementsSatisfied < this.maxBlockingRequirements)
			return false;
		else if(this.maxBlockingRequirements < requirementsSatisfied)
			throw new IllegalStateException(this.maxBlockingRequirements + " requirements maximum; " + requirementsSatisfied + " requirements satisfied");

		// TODO : Permissions

		// TODO : 509.1d If any of the chosen creatures require paying costs to
		// block, the defending player determines the total cost to block.

		// TODO : 509.1e If any of the costs require mana, the defending player
		// then has a chance to activate mana abilities.

		// TODO : 509.1f Once the player has enough mana in his or her mana
		// pool, he or she pays all costs in any order..

		// Rule 309.2e 509.1g Each chosen creature still controlled by the
		// defending player becomes a blocking creature. Each one is blocking
		// the attacking creatures chosen for it. It remains a blocking creature
		// until it's removed from combat, an effect says that it becomes
		// unblocked, or the combat phase ends, whichever comes first.
		this.game.physicalState.currentPhase().blockersDeclared = true;
		for(GameObject blocker: physicalBlockers)
		{
			Event declareBlockerEvent = new Event(this.game.physicalState, blocker + " blocks.", EventType.DECLARE_ONE_BLOCKER);
			declareBlockerEvent.parameters.put(EventType.Parameter.OBJECT, Identity.instance(blocker));
			declareBlockerEvent.parameters.put(EventType.Parameter.ATTACKER, IdentifiedWithID.instance(blocker.getBlockingIDs()));
			declareBlockerEvent.perform(this.parent, false);
		}

		// 509.1h An attacking creature with one or more creatures declared as
		// blockers for it becomes a blocked creature; one with no creatures
		// declared as blockers for it becomes an unblocked creature.
		for(GameObject attacker: attackers)
		{
			if(attacker.getBlockedByIDs() == null)
			{
				// Unblocked
				Event unblockedEvent = new Event(this.game.physicalState, attacker.getName() + " is unblocked.", EventType.BECOMES_UNBLOCKED);
				unblockedEvent.parameters.put(EventType.Parameter.ATTACKER, IdentifiedWithID.instance(attacker.ID));
				unblockedEvent.perform(this.parent, false);
			}
			else
			{
				// Blocked
				Event blockedEvent = new Event(this.game.physicalState, attacker.getName() + " is blocked.", EventType.BECOMES_BLOCKED);
				blockedEvent.parameters.put(EventType.Parameter.ATTACKER, IdentifiedWithID.instance(attacker.ID));
				blockedEvent.parameters.put(EventType.Parameter.DEFENDER, IdentifiedWithID.instance(attacker.getBlockedByIDs()));
				blockedEvent.perform(this.parent, false);
			}
		}

		return true;
	}

	private int satisfiedBlockingRequirements()
	{
		int requirementsSatisfied = 0;
		for(BlockingRequirement r: this.requirementsToCheck)
			if(r.isSatisfied(this.game.actualState))
				++requirementsSatisfied;
		return requirementsSatisfied;
	}

	@Override
	public PlayerInterface.ReversionParameters getReversionReason()
	{
		Player player = this.game.physicalState.get(this.actorID);
		return new PlayerInterface.ReversionParameters("DeclareBlockersAction", player.getName() + " declared an illegal block.");
	}
}
