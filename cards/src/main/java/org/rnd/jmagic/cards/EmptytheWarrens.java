package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Empty the Warrens")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class EmptytheWarrens extends Card
{
	public EmptytheWarrens(GameState state)
	{
		super(state);

		// Put two 1/1 red Goblin creature tokens onto the battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(2, 1, 1, "Put two 1/1 red Goblin creature tokens onto the battlefield.");
		factory.setColors(Color.RED);
		factory.setSubTypes(SubType.GOBLIN);
		this.addEffect(factory.getEventFactory());

		// Storm (When you cast this spell, copy it for each spell cast before
		// it this turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Storm(state));
	}
}
