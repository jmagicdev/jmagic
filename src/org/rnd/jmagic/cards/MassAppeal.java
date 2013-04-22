package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mass Appeal")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class MassAppeal extends Card
{
	public MassAppeal(GameState state)
	{
		super(state);

		// Draw a card for each Human you control.
		SetGenerator number = Count.instance(Intersect.instance(HasSubType.instance(SubType.HUMAN), ControlledBy.instance(You.instance())));
		this.addEffect(drawCards(You.instance(), number, "Draw a card for each Human you control."));
	}
}
