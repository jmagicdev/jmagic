package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Steel Sabotage")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SteelSabotage extends Card
{
	public SteelSabotage(GameState state)
	{
		super(state);

		// Choose one \u2014 Counter target artifact spell; or return target
		// artifact to its owner's hand.

		{
			SetGenerator target = targetedBy(this.addTarget(1, Intersect.instance(Spells.instance(), HasType.instance(Type.ARTIFACT)), "target artifact spell"));
			this.addEffect(1, counter(target, "Counter target arifact spell"));
		}

		{
			SetGenerator target = targetedBy(this.addTarget(2, ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(2, bounce(target, "return target artifact to its owner's hand."));
		}
	}
}
