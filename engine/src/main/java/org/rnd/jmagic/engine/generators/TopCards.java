package org.rnd.jmagic.engine.generators;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the top number of {@link Card}s of each given {@link Zone}. If
 * no number is specified (for instance if an {@link IndexOf} took an empty set
 * (see {@link org.rnd.jmagic.cards.Polymorph})) return all the cards in all the
 * given zones. {@link GameObject}s that are not {@link Card}s will not count.
 */
public class TopCards extends SetGenerator
{
	public static TopCards instance(int number, SetGenerator zones)
	{
		return new TopCards(numberGenerator(number), zones);
	}

	public static TopCards instance(SetGenerator number, SetGenerator zones)
	{
		return new TopCards(number, zones);
	}

	public static Set get(int number, Zone zone)
	{
		Set ret = new Set();
		int count = 0;
		for(GameObject o: zone)
		{
			if(count < number)
			{
				if(o.isCard())
				{
					ret.add(o);
					++count;
				}
			}
			else
				break;
		}
		return ret;
	}

	private final SetGenerator number;
	private final SetGenerator zones;

	private TopCards(SetGenerator number, SetGenerator zones)
	{
		this.number = number;
		this.zones = zones;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set numberSet = this.number.evaluate(state, thisObject);

		if(numberSet.isEmpty())
		{
			Set ret = new Set();

			for(Zone zone: this.zones.evaluate(state, thisObject).getAll(Zone.class))
				ret.addAll(zone.objects);

			return ret;
		}

		int number = Maximum.get(numberSet);
		Set ret = new Set();
		for(Zone zone: this.zones.evaluate(state, thisObject).getAll(Zone.class))
			ret.addAll(TopCards.get(number, zone));
		return ret;
	}
}
