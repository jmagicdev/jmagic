package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Empty the Pits")
@Types({Type.INSTANT})
@ManaCost("XXBBBB")
@ColorIdentity({Color.BLACK})
public final class EmptythePits extends Card
{
	public EmptythePits(GameState state)
	{
		super(state);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Put X 2/2 black Zombie creature tokens onto the battlefield tapped.
		SetGenerator X = ValueOfX.instance(This.instance());

		CreateTokensFactory zombies = new CreateTokensFactory(X, numberGenerator(2), numberGenerator(2), "");
		zombies.setColors(Color.BLACK);
		zombies.setSubTypes(SubType.ZOMBIE);
		this.addEffect(zombies.getEventFactory());
	}
}
