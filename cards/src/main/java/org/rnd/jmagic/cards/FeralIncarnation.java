package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Feral Incarnation")
@Types({Type.SORCERY})
@ManaCost("8G")
@ColorIdentity({Color.GREEN})
public final class FeralIncarnation extends Card
{
	public FeralIncarnation(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Put three 3/3 green Beast creature tokens onto the battlefield.
		CreateTokensFactory token = new CreateTokensFactory(3, 3, 3, "Put three 3/3 green Beast creature tokens onto the battlefield.");
		token.setColors(Color.GREEN);
		token.setSubTypes(SubType.BEAST);
		this.addEffect(token.getEventFactory());
	}
}
