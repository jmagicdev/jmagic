package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gather the Townsfolk")
@Types({Type.SORCERY})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class GathertheTownsfolk extends Card
{
	public GathertheTownsfolk(GameState state)
	{
		super(state);

		// Put two 1/1 white Human creature tokens onto the battlefield. If you
		// have 5 or less life, put five of those tokens onto the battlefield
		// instead.
		SetGenerator number = IfThenElse.instance(FatefulHour.instance(), numberGenerator(5), numberGenerator(2));

		CreateTokensFactory factory = new CreateTokensFactory(number, "Put two 1/1 white Human creature tokens onto the battlefield.\n\nFateful hour \u2014 If you have 5 or less life, put five of those tokens onto the battlefield instead.");
		factory.addCreature(1, 1);
		factory.setColors(Color.WHITE);
		factory.setSubTypes(SubType.HUMAN);
		this.addEffect(factory.getEventFactory());
	}
}
