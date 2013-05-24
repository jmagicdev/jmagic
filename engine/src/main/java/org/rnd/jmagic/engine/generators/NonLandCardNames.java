package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class NonLandCardNames extends SetGenerator
{
	private static final NonLandCardNames _instance = new NonLandCardNames();

	// I really don't want to do this work twice, so we'll store the result here
	// the first time.
	private static Set set = null;

	public static NonLandCardNames instance()
	{
		return _instance;
	}

	public static Set get()
	{
		if(set == null)
		{
			Set ret = new Set();
			cardLoop: for(Class<? extends Card> card: org.rnd.jmagic.CardLoader.getAllCards())
			{
				Types types = card.getAnnotation(Types.class);
				if(types != null)
				{
					for(Type type: types.value())
					{
						if(type.equals(Type.LAND))
							continue cardLoop;
					}
					Name name = card.getAnnotation(Name.class);
					if(name != null)
						ret.add(name.value());
				}
			}
			set = new Set.Unmodifiable(ret);
		}
		return set;
	}

	private NonLandCardNames()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return get();
	}

}
