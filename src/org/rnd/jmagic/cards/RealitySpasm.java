package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reality Spasm")
@Types({Type.INSTANT})
@ManaCost("XUU")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class RealitySpasm extends Card
{
	public RealitySpasm(GameState state)
	{
		super(state);

		// Choose one \u2014 Tap X target permanents; or untap X target
		// permanents.
		{
			Target xTargetPermanents = this.addTarget(1, Permanents.instance(), "X target permanents");
			xTargetPermanents.setSingleNumber(ValueOfX.instance(This.instance()));
			this.addEffect(1, tap(targetedBy(xTargetPermanents), "Tap X target permanents"));
		}

		{
			Target xTargetPermanents = this.addTarget(2, Permanents.instance(), "X target permanents");
			xTargetPermanents.setSingleNumber(ValueOfX.instance(This.instance()));
			this.addEffect(2, untap(targetedBy(xTargetPermanents), "untap X target permanents."));
		}
	}
}
