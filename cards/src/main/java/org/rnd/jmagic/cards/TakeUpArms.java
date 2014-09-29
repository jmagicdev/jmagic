package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Take Up Arms")
@Types({Type.INSTANT})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class TakeUpArms extends Card
{
	public TakeUpArms(GameState state)
	{
		super(state);

		// Put three 1/1 white Warrior creature tokens onto the battlefield.
		CreateTokensFactory warriors = new CreateTokensFactory(3, 1, 1, "Put three 1/1 white Warrior creature tokens onto the battlefield.");
		warriors.setColors(Color.WHITE);
		warriors.setSubTypes(SubType.WARRIOR);
		this.addEffect(warriors.getEventFactory());
	}
}
