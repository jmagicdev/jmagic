package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blue Elemental Blast")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class BlueElementalBlast extends Card
{
	public BlueElementalBlast(GameState state)
	{
		super(state);

		// Choose one \u2014 Counter target red spell; or destroy target red
		// permanent.
		SetGenerator redSpells = Intersect.instance(HasColor.instance(Color.RED), Spells.instance());
		SetGenerator target1 = targetedBy(this.addTarget(1, redSpells, "target red spell"));
		this.addEffect(1, counter(target1, "Counter target red spell"));

		SetGenerator redPermanents = Intersect.instance(HasColor.instance(Color.RED), Permanents.instance());
		SetGenerator target2 = targetedBy(this.addTarget(2, redPermanents, "target red permanent"));
		this.addEffect(2, destroy(target2, "destroy target red permanent"));
	}
}
