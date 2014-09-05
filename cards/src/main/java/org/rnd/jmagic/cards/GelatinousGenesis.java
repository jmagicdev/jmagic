package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gelatinous Genesis")
@Types({Type.SORCERY})
@ManaCost("XXG")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class GelatinousGenesis extends Card
{
	public GelatinousGenesis(GameState state)
	{
		super(state);

		// Put X X/X green Ooze creature tokens onto the battlefield.
		SetGenerator x = ValueOfX.instance(This.instance());
		CreateTokensFactory tokens = new CreateTokensFactory(x, x, x, "Put X X/X green Ooze creature tokens onto the battlefield.");
		tokens.setColors(Color.GREEN);
		tokens.setSubTypes(SubType.OOZE);
		this.addEffect(tokens.getEventFactory());
	}
}
