package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Disperse")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON), @Printings.Printed(ex = Morningtide.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Disperse extends Card
{
	public Disperse(GameState state)
	{
		super(state);

		// Return target nonland permanent to its owner's hand.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), LandPermanents.instance()), "target nonland permanent"));
		this.addEffect(bounce(target, "Return target nonland permanent to its owner's hand."));
	}
}
