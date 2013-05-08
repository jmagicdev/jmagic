package org.rnd.jmagic.engine;

public class AttackingRequirement
{
	private int attackingID;
	private int whoToAttackID;

	/**
	 * Creates an attacking requirement where the specified creature must
	 * attack.
	 * 
	 * @param attacking The creature that must be attacking to satisfy this
	 * requirement.
	 */
	public AttackingRequirement(GameObject attacking)
	{
		this.attackingID = attacking.ID;
		this.whoToAttackID = -1;
	}

	/**
	 * Creates an attacking requirement where the specified creature must attack
	 * a specific player or planeswalker.
	 * 
	 * @param attacking The creature that must be attacking to satisfy this
	 * requirement.
	 * @param whoToAttack Who the creature must attack.
	 */
	public AttackingRequirement(GameObject attacking, Identified whoToAttack)
	{
		this(attacking);

		this.whoToAttackID = whoToAttack.ID;
	}

	/**
	 * "Optimizes" this blocking requirement by removing from the blocking set
	 * all creatures not controlled by the specified player.
	 * 
	 * @param attacker The attacking player to optimize around
	 * @param state The state in which to evaluate who controls which creatures
	 * @return Whether this requirement still has any attacking creatures after
	 * the optimization
	 */
	public boolean attackingPlayerIs(Player attacker, GameState state)
	{
		if(state.getByIDObject(this.attackingID).controllerID != attacker.ID)
		{
			this.attackingID = -1;
			return false;
		}
		return true;
	}

	public boolean isSatisfied(GameState state)
	{
		GameObject attacker = state.get(this.attackingID);

		if(-1 == this.whoToAttackID)
		{
			if(-1 != attacker.getAttackingID())
				return true;

			java.util.Map<Integer, java.util.Set<Integer>> successfullyAttacked = state.getTracker(SuccessfullyAttacked.class).getValue(state);
			return (successfullyAttacked.containsKey(attacker.ID)) && !(successfullyAttacked.get(attacker.ID).isEmpty());
		}

		if(this.whoToAttackID == attacker.getAttackingID())
			return true;

		java.util.Map<Integer, java.util.Set<Integer>> successfullyAttacked = state.getTracker(SuccessfullyAttacked.class).getValue(state);
		return (successfullyAttacked.containsKey(attacker.ID)) && successfullyAttacked.get(attacker.ID).contains(this.whoToAttackID);
	}
}
