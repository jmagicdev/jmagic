package org.rnd.jmagic.engine.gameTypes.packWars;

import org.rnd.jmagic.engine.*;

@Name("Vintage booster")
public class VintageBoosterFactory implements BoosterFactory
{
	private static java.util.Random rand = new java.util.Random();

	@Override
	public java.util.List<Card> createBooster(GameState state) throws org.rnd.jmagic.CardLoader.CardLoaderException
	{
		java.util.List<Card> ret = new java.util.LinkedList<Card>();
		java.util.Set<Class<? extends Card>> pool = org.rnd.jmagic.CardLoader.getAllCards();

		cardLoop: for(int i = 0; i < 15; ++i)
		{
			int k = rand.nextInt(pool.size());

			java.util.Iterator<Class<? extends Card>> iter = pool.iterator();

			for(int j = 1; j < k; ++j)
				iter.next();

			Class<? extends Card> card = iter.next();

			// Ignore any basics
			SuperTypes superTypes = card.getAnnotation(SuperTypes.class);
			if(superTypes != null)
				for(SuperType superType: superTypes.value())
					if(superType.equals(SuperType.BASIC))
					{
						--i;
						continue cardLoop;
					}

			// All the types should share 'Traditional-ness', so only check one.
			Types types = card.getAnnotation(Types.class);
			if(types != null && types.value()[0].isTraditional())
				ret.add(org.rnd.util.Constructor.construct(card, new Class<?>[] {GameState.class}, new Object[] {state}));
			else
				--i;
		}

		return ret;
	}
}
