package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Consult the Necrosages")
@Types({Type.SORCERY})
@ManaCost("1UB")
@Printings({@Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class ConsulttheNecrosages extends Card
{
	public ConsulttheNecrosages(GameState state)
	{
		super(state);

		// Mode 1
		{
			Target target = this.addTarget(1, Players.instance(), "target player");
			this.addEffect(1, drawCards(targetedBy(target), 2, "Target player draws two cards."));
		}

		// Mode 2
		{
			Target target = this.addTarget(2, Players.instance(), "target player");
			this.addEffect(2, drawCards(targetedBy(target), 2, "Target player discards two cards."));
		}
	}
}
