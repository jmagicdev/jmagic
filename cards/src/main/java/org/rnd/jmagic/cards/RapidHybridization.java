package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rapid Hybridization")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class RapidHybridization extends Card
{
	public RapidHybridization(GameState state)
	{
		super(state);

		// Destroy target creature. It can't be regenerated. That creature's
		// controller puts a 3/3 green Frog Lizard creature token onto the
		// battlefield.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffects(bury(this, target, "Destroy target creature. It can't be regenerated."));

		CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "That creature's controller puts a 3/3 green Frog Lizard creature token onto the battlefield.");
		token.setController(ControllerOf.instance(target));
		token.setColors(Color.GREEN);
		token.setSubTypes(SubType.FROG, SubType.LIZARD);
		this.addEffect(token.getEventFactory());
	}
}
