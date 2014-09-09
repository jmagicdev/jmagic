package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Befoul")
@Types({Type.SORCERY})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class Befoul extends Card
{
	public Befoul(GameState state)
	{
		super(state);

		// Destroy target land or nonblack creature. It can't be regenerated.
		SetGenerator nonblackCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK));
		SetGenerator landOrNonblackCreatures = Union.instance(LandPermanents.instance(), nonblackCreatures);
		SetGenerator target = targetedBy(this.addTarget(landOrNonblackCreatures, "target land or nonblack creature"));
		this.addEffects(bury(this, target, "Destroy target land or nonblack creature. It can't be regenerated."));
	}
}
