package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Hunters' Feast")
@Types({Type.SORCERY})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class HuntersFeast extends Card
{
	public HuntersFeast(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");
		target.setNumber(0, null);
		this.addEffect(gainLife(targetedBy(target), 6, "Any number of target players each gain 6 life."));
	}
}
