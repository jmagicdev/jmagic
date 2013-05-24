package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vanishment")
@Types({Type.INSTANT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Vanishment extends Card
{
	public Vanishment(GameState state)
	{
		super(state);

		// Put target nonland permanent on top of its owner's library.
		SetGenerator legal = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
		SetGenerator target = targetedBy(this.addTarget(legal, "target nonland permanent"));
		this.addEffect(putOnTopOfLibrary(target, "Put target nonland permanent on top of its owner's library."));

		// Miracle (U)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(U)"));
	}
}
