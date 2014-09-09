package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Slime Molding")
@Types({Type.SORCERY})
@ManaCost("XG")
@ColorIdentity({Color.GREEN})
public final class SlimeMolding extends Card
{
	public SlimeMolding(GameState state)
	{
		super(state);

		// Put an X/X green Ooze creature token onto the battlefield.
		SetGenerator x = ValueOfX.instance(This.instance());
		CreateTokensFactory token = new CreateTokensFactory(numberGenerator(1), x, x, "Put an X/X green Ooze creature token onto the battlefield.");
		token.setColors(Color.GREEN);
		token.setSubTypes(SubType.OOZE);
		this.addEffect(token.getEventFactory());
	}
}
