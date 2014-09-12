package org.rnd.jmagic.engine;

public class BlockingCost
{
	private Set cost;
	private int number;
	private java.util.Set<Integer> creatureIDs;

	/**
	 * Creates a new attacking cost.
	 * 
	 * @param state The state to get creatures and players from.
	 * @param cost The cost for each creature specified to attack.
	 * @param creatures The set of creatures this cost applies to. If it's null,
	 * it applies to all creatures.
	 */
	public BlockingCost(GameState state, int number, Set cost, Set creatures)
	{
		this.cost = cost;
		this.number = number;

		this.creatureIDs = new java.util.HashSet<Integer>();
		for(GameObject o: creatures.getAll(GameObject.class))
			this.creatureIDs.add(o.ID);
	}

	/**
	 * @param blockers The set of blockers to check this cost against.
	 * @return The total cost to attack that this attacking cost imposes. Null
	 * if this attacking cost does not apply to these attackers at all.
	 */
	public Set evaluate(java.util.Collection<GameObject> blockers)
	{
		Set ret = new Set();
		boolean applies = false;
		for(GameObject blocker: blockers)
			if(this.creatureIDs.contains(blocker.ID))
			{
				// Most of the time, the cost will be a mana cost. Calling
				// addAll on the same set of mana symbols multiple times will
				// only add those mana symbols once, so we duplicate the mana
				// before adding it. -RulesGuru
				for(ManaSymbol m: this.cost.getAll(ManaSymbol.class))
					for(int i = 0; i < this.number; i++)
						ret.add(m.create());
				applies = true;

				// TODO : The same needs to be done for events, yes? We don't
				// have Event.create() (RW - we do now...), and Event.clone() is
				// not used for this kind of thing.
				Event eventCost = this.cost.getOne(Event.class);
				if(eventCost != null)
					ret.add(eventCost);
			}

		return applies ? ret : null;
	}
}
