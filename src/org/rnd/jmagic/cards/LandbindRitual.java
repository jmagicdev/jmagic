package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Landbind Ritual")
@Types({Type.SORCERY})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class LandbindRitual extends Card
{
	public LandbindRitual(GameState state)
	{
		super(state);

		// You gain 2 life for each Plains you control.
		SetGenerator countYourPlains = Count.instance(Intersect.instance(HasSubType.instance(SubType.PLAINS), ControlledBy.instance(You.instance())));
		SetGenerator amount = Multiply.instance(numberGenerator(2), countYourPlains);
		this.addEffect(gainLife(You.instance(), amount, "You gain 2 life for each Plains you control."));
	}
}
