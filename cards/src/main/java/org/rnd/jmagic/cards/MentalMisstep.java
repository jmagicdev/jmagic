package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mental Misstep")
@Types({Type.INSTANT})
@ManaCost("(U/P)")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class MentalMisstep extends Card
{
	public MentalMisstep(GameState state)
	{
		super(state);

		// ((u/p) can be paid with either (U) or 2 life.)

		// Counter target spell with converted mana cost 1.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Spells.instance(), HasConvertedManaCost.instance(numberGenerator(1))), "target spell with converted mana cost 1"));
		this.addEffect(counter(target, "Counter target spell with converted mana cost 1."));
	}
}
