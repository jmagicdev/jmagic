package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Essence Harvest")
@Types({Type.SORCERY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class EssenceHarvest extends Card
{
	public EssenceHarvest(GameState state)
	{
		super(state);

		// Target player loses X life and you gain X life, where X is the
		// greatest power among creatures you control.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator X = Maximum.instance(PowerOf.instance(CREATURES_YOU_CONTROL));
		this.addEffect(loseLife(target, X, "Target player loses X life"));
		this.addEffect(gainLife(You.instance(), X, "and you gain X life, where X is the greatest power among creatures you control."));
	}
}
