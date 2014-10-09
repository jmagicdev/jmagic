package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Revel of the Fallen God")
@Types({Type.SORCERY})
@ManaCost("3RRGG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class ReveloftheFallenGod extends Card
{
	public ReveloftheFallenGod(GameState state)
	{
		super(state);

		// Put four 2/2 red and green Satyr creature tokens with haste onto the
		// battlefield.
		CreateTokensFactory satyrs = new CreateTokensFactory(4, 2, 2, "Put four 2/2 red and green Satyr creature tokens with haste onto the battlefield.");
		satyrs.setColors(Color.RED, Color.GREEN);
		satyrs.setSubTypes(SubType.SATYR);
		satyrs.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
		this.addEffect(satyrs.getEventFactory());
	}
}
