package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the smallest contiguous set containing {@code N} members of
 * {@code cards} starting from the top of the ordered {@code zone}. (READ: The
 * set will contain all the cards from the top of the given zone to the N-th
 * card in that zone that matches the given filter [inclusive]).
 */
public class TopMost extends SetGenerator
{
	public static TopMost instance(SetGenerator zone, SetGenerator N, SetGenerator cards)
	{
		return new TopMost(zone, N, cards);
	}

	private final SetGenerator zone;
	private final SetGenerator N;
	private final SetGenerator cards;

	private TopMost(SetGenerator zone, SetGenerator N, SetGenerator cards)
	{
		this.zone = zone;
		this.N = N;
		this.cards = cards;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		int N = this.N.evaluate(state, thisObject).getOne(Integer.class);
		if(N == 0)
			return Empty.set;

		Zone zone = this.zone.evaluate(state, thisObject).getOne(Zone.class);
		java.util.Set<GameObject> cards = this.cards.evaluate(state, thisObject).getAll(GameObject.class);

		int count = 0;
		Set ret = new Set();
		for(GameObject card: zone.objects)
		{
			ret.add(card);
			if(cards.contains(card))
			{
				count++;
				if(count == N)
					break;
			}
		}
		return ret;
	}
}