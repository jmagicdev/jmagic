package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Battle Hymn")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class BattleHymn extends Card
{
	public BattleHymn(GameState state)
	{
		super(state);

		// Add (R) to your mana pool for each creature you control.
		EventFactory factory = new EventFactory(EventType.ADD_MANA, "Add (R) to your mana pool for each creature you control.");
		factory.parameters.put(EventType.Parameter.SOURCE, This.instance());
		factory.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(R)")));
		factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(CREATURES_YOU_CONTROL));
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(factory);
	}
}
