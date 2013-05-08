package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each attacking creature, or to each creature attacking a given
 * object. It is safe to assume that this will only return creatures.
 */
public class Attacking extends SetGenerator
{
	private static final Attacking _instance = new Attacking();

	public static Attacking instance()
	{
		return _instance;
	}

	public static Attacking instance(SetGenerator what)
	{
		return new Attacking(what);
	}

	private final SetGenerator what;

	private Attacking()
	{
		this.what = null;
	}

	private Attacking(SetGenerator what)
	{
		this.what = what;
	}

	public static Set get(GameState state)
	{
		Set ret = new Set();
		for(GameObject o: state.battlefield())
			if(-1 != o.getAttackingID())
				ret.add(o);
		return ret;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set allAttackers = Attacking.get(state);
		if(this.what == null)
			return allAttackers;

		java.util.Set<Integer> beingAttackedIDs = new java.util.HashSet<Integer>();
		Set evaluateWhat = this.what.evaluate(state, thisObject);
		for(GameObject o: evaluateWhat.getAll(GameObject.class))
			if(o.getTypes().contains(Type.PLANESWALKER))
				beingAttackedIDs.add(o.ID);
		for(Player p: evaluateWhat.getAll(Player.class))
			beingAttackedIDs.add(p.ID);

		java.util.Set<GameObject> attackingThisPlayer = allAttackers.getAll(GameObject.class);
		java.util.Iterator<GameObject> objectIterator = attackingThisPlayer.iterator();
		while(objectIterator.hasNext())
		{
			GameObject attacker = objectIterator.next();
			if(!beingAttackedIDs.contains(attacker.getAttackingID()))
				objectIterator.remove();
		}

		return new Set(attackingThisPlayer);
	}
}
