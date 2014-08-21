package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class EmptyManaPool extends EventType
{
	public static final EventType INSTANCE = new EmptyManaPool();

	private EmptyManaPool()
	{
		super("EMPTY_MANA_POOL");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
		Set result = new Set();
		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			// If the cause is the game, then mana pools are being emptied
			// as a step or phase ends, and we may have some work to do.
			if(cause == null)
			{
				SetPattern doesntEmpty = game.actualState.manaThatDoesntEmpty.get(player.ID);
				java.util.Iterator<ManaSymbol> m = player.getPhysical().pool.iterator();
				while(m.hasNext())
				{
					ManaSymbol mana = m.next();
					if(!doesntEmpty.match(game.actualState, null, new Set(mana)))
					{
						result.add(mana);
						m.remove();
					}
				}
			}
			// Otherwise an effect is doing it and continuous effects will
			// do nothing to stop it.
			else
			{
				result.addAll(player.pool);
				player.getPhysical().pool.clear();
			}
		}

		event.setResult(Identity.fromCollection(result));
		return true;
	}
}