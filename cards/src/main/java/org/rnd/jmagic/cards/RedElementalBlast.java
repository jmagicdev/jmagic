package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Red Elemental Blast")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RedElementalBlast extends Card
{
	public RedElementalBlast(GameState state)
	{
		super(state);

		// Choose one \u2014 Counter target blue spell; or destroy target blue
		// permanent.
		SetGenerator blueSpells = Intersect.instance(HasColor.instance(Color.BLUE), Spells.instance());
		SetGenerator target1 = targetedBy(this.addTarget(1, blueSpells, "target blue spell"));
		this.addEffect(1, counter(target1, "Counter target blue spell."));

		SetGenerator bluePermanents = Intersect.instance(HasColor.instance(Color.BLUE), Permanents.instance());
		SetGenerator target2 = targetedBy(this.addTarget(2, bluePermanents, "target blue permanent"));
		this.addEffect(2, destroy(target2, "Destroy target blue permanent."));
	}
}
