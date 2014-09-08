package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class CardNames extends SetGenerator
{
	private static final CardNames _instance = new CardNames();

	// I really don't want to do this work twice, so we'll store the result here
	// the first time.
	private static Set set = null;

	public static CardNames instance()
	{
		return _instance;
	}

	public static Set get()
	{
		if(set == null)
		{
			Set ret = new Set();
			for(Class<? extends Card> card: org.rnd.jmagic.CardLoader.getAllCards())
			{
				Name name = card.getAnnotation(Name.class);
				if(name != null)
					// when naming cards for meddling mage, you name one
					// side of a split card, not both sides.
					java.util.Arrays.stream(name.value().split(" // ")).forEach(n -> ret.add(n));
			}
			set = new Set.Unmodifiable(ret);
		}
		return set;
	}

	private CardNames()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return get();
	}

}
