package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dispel")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Dispel extends Card
{
	public Dispel(GameState state)
	{
		super(state);

		// Counter target instant spell.
		Target target = this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT), Spells.instance()), "target instant spell");
		this.addEffect(counter(targetedBy(target), "Counter target instant spell."));
	}
}
