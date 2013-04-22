package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Reap the Seagraf")
@Types({Type.SORCERY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class ReaptheSeagraf extends Card
{
	public ReaptheSeagraf(GameState state)
	{
		super(state);

		// Put a 2/2 black Zombie creature token onto the battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
		factory.setColors(Color.BLACK);
		factory.setSubTypes(SubType.ZOMBIE);
		this.addEffect(factory.getEventFactory());

		// Flashback (4)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(4)(U)"));
	}
}
