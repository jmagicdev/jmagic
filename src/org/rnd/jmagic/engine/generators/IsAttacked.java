package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each object and player being attacked (optionally by a specific
 * object)
 */
public class IsAttacked extends SetGenerator
{
	private static final IsAttacked _instance = new IsAttacked();

	public static IsAttacked instance()
	{
		return _instance;
	}

	public static IsAttacked instance(SetGenerator what)
	{
		return new IsAttacked(what);
	}

	public static Set get(GameState state)
	{
		Set ret = new Set();

		players: for(Player player: Players.get(state).getAll(Player.class))
		{
			for(Integer id: player.defendingIDs)
			{
				GameObject o = state.get(id);
				if(!o.isGhost() && o.getAttackingID() == player.ID)
				{
					ret.add(player);
					continue players;
				}
			}
			for(GameObject object: ControlledBy.instance(Identity.instance(player)).evaluate(state, null).getAll(GameObject.class))
				for(Integer id: object.getDefendingIDs())
				{
					GameObject o = state.get(id);
					if(!o.isGhost() && o.getAttackingID() == object.ID)
					{
						ret.add(player);
						continue players;
					}
				}
		}

		return ret;
	}

	private final SetGenerator what;

	private IsAttacked()
	{
		this.what = null;
	}

	private IsAttacked(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(this.what == null)
			return IsAttacked.get(state);

		Set ret = new Set();
		Set attackers = this.what.evaluate(state, thisObject);

		for(GameObject attacker: attackers.getAll(GameObject.class))
			if(attacker.getAttackingID() != -1)
				if(state.containsIdentified(attacker.getAttackingID()))
					ret.add(state.get(attacker.getAttackingID()));

		return ret;
	}
}
