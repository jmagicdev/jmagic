package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fungal Sprouting")
@Types({Type.SORCERY})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class FungalSprouting extends Card
{
	public FungalSprouting(GameState state)
	{
		super(state);

		// Put X 1/1 green Saproling creature tokens onto the battlefield, where
		// X is the greatest power among creatures you control.

		SetGenerator X = Maximum.instance(PowerOf.instance(CREATURES_YOU_CONTROL));
		SetGenerator one = numberGenerator(1);

		CreateTokensFactory token = new CreateTokensFactory(X, one, one, "Put X 1/1 green Saproling creature tokens onto the battlefield, where X is the greatest power among creatures you control.");
		token.setColors(Color.GREEN);
		token.setSubTypes(SubType.SAPROLING);
		this.addEffect(token.getEventFactory());
	}
}
