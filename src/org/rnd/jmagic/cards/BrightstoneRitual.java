package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brightstone Ritual")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BrightstoneRitual extends Card
{
	public BrightstoneRitual(GameState state)
	{
		super(state);

		// Add (R) to your mana pool for each Goblin on the battlefield.
		SetGenerator goblins = HasSubType.instance(SubType.GOBLIN);
		SetGenerator goblinsOnTheBattlefield = Intersect.instance(CreaturePermanents.instance(), goblins);
		EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add (R) to your mana pool for each Goblin on the battlefield.");
		addMana.parameters.put(EventType.Parameter.SOURCE, This.instance());
		addMana.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("(R)")));
		addMana.parameters.put(EventType.Parameter.NUMBER, Count.instance(goblinsOnTheBattlefield));
		addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(addMana);
	}
}
