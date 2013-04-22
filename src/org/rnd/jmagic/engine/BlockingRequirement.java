package org.rnd.jmagic.engine;

public class BlockingRequirement
{
	private java.util.Collection<Integer> attacking;
	private org.rnd.util.NumberRange blockingRange;
	private java.util.Collection<Integer> blocking;

	/**
	 * @param attacking The set of creatures that can be blocked to satisfy this
	 * requirement.
	 * @param blockingRange The number of creatures from the blocking set
	 * allowed to block a creature from the attacking set in order to satisfy
	 * this requirement.
	 * @param blocking The set of creatures that can block to satisfy this
	 * requirement.
	 */
	public BlockingRequirement(java.util.Collection<GameObject> attacking, org.rnd.util.NumberRange blockingRange, java.util.Collection<GameObject> blocking)
	{
		this.attacking = new java.util.LinkedList<Integer>();
		for(GameObject o: attacking)
			this.attacking.add(o.ID);
		this.blockingRange = blockingRange;
		this.blocking = new java.util.LinkedList<Integer>();
		for(GameObject o: blocking)
			this.blocking.add(o.ID);
	}

	/**
	 * "Optimizes" this blocking requirement by removing from the blocking set
	 * all creatures not controlled by the specified player.
	 * 
	 * @param defender The defending player to optimize around
	 * @param state The state in which to evaluate who controls which creatures
	 * @return Whether this requirement still has any blocking creatures after
	 * the optimization
	 */
	public boolean defendingPlayerIs(Player defender, GameState state)
	{
		java.util.Iterator<Integer> i = this.blocking.iterator();
		while(i.hasNext())
			if(state.getByIDObject(i.next()).controllerID != defender.ID)
				i.remove();

		return !this.blocking.isEmpty();
	}

	public boolean isSatisfied(GameState state)
	{
		int blocks = 0;
		for(Integer blockerID: this.blocking)
		{
			GameObject blocker = state.get(blockerID);
			for(Integer beingBlockedID: blocker.getBlockingIDs())
				if(this.attacking.contains(beingBlockedID))
					++blocks;
		}
		return this.blockingRange.contains(blocks);
	}
}
