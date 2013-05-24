package org.rnd.jmagic.engine;

public class AttackingCost
{
	private Set cost;
	private int number;
	private java.util.Set<Integer> creatureIDs;
	private java.util.Set<Integer> playerIDs;

	/**
	 * Creates a new attacking cost.
	 * 
	 * @param state The state to get creatures and players from.
	 * @param cost The cost for each creature specified to attack.
	 * @param creatures The set of creatures this cost applies to. If it's null,
	 * it applies to all creatures.
	 * @param players The set of players who, if attacked, will invoke this
	 * cost. If it's null, it applies to attacking any player.
	 */
	public AttackingCost(GameState state, int number, Set cost, Set creatures, Set players)
	{
		this.cost = cost;
		this.number = number;

		this.creatureIDs = new java.util.HashSet<Integer>();
		for(GameObject o: creatures.getAll(GameObject.class))
			this.creatureIDs.add(o.ID);

		this.playerIDs = new java.util.HashSet<Integer>();
		if(players != null)
			for(Player p: players.getAll(Player.class))
				this.playerIDs.add(p.ID);
		else
			for(Player p: state.players)
				this.playerIDs.add(p.ID);
	}

	/**
	 * @param attackers The set of attackers to check this cost against.
	 * @return The total cost to attack that this attacking cost imposes. Null
	 * if this attacking cost does not apply to these attackers at all.
	 */
	public Set evaluate(java.util.Collection<GameObject> attackers)
	{
		Set ret = new Set();
		boolean applies = false;
		for(GameObject attacker: attackers)
			if(this.creatureIDs.contains(attacker.ID) && this.playerIDs.contains(attacker.getAttackingID()))
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
