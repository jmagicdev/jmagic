package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vandalblast")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Vandalblast extends Card
{
	public Vandalblast(GameState state)
	{
		super(state);

		// Destroy target artifact you don't control.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance())), "target artifact you don't control"));
		this.addEffect(destroy(target, "Destroy target artifact you don't control."));

		// Overload (4)(R) (You may cast this spell for its overload cost. If
		// you do, change its text by replacing all instances of "target" with
		// "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(4)(R)"));
	}
}
