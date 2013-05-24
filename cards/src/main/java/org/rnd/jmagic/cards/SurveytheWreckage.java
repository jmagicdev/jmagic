package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Survey the Wreckage")
@Types({Type.SORCERY})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SurveytheWreckage extends Card
{
	public SurveytheWreckage(GameState state)
	{
		super(state);

		// Destroy target land. Put a 1/1 red Goblin creature token onto the
		// battlefield.
		SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
		this.addEffect(destroy(target, "Destroy target land."));

		CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 red Goblin creature token onto the battlefield.");
		token.setColors(Color.RED);
		token.setSubTypes(SubType.GOBLIN);
		this.addEffect(token.getEventFactory());
	}
}
