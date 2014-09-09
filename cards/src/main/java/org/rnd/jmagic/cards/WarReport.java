package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("War Report")
@Types({Type.INSTANT})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class WarReport extends Card
{
	public WarReport(GameState state)
	{
		super(state);

		// You gain life equal to the number of creatures on the battlefield
		// plus the number of artifacts on the battlefield.
		SetGenerator numCreatures = Count.instance(CreaturePermanents.instance());
		SetGenerator numArtifacts = Count.instance(ArtifactPermanents.instance());
		SetGenerator sum = Sum.instance(Union.instance(numCreatures, numArtifacts));
		this.addEffect(gainLife(You.instance(), sum, "You gain life equal to the number of creatures on the battlefield plus the number of artifacts on the battlefield."));
	}
}
