package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Prey Upon")
@Types({Type.SORCERY})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class PreyUpon extends Card
{
	public PreyUpon(GameState state)
	{
		super(state);

		SetGenerator targetOne = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), CREATURES_YOU_CONTROL), "target creature you control"));
		SetGenerator targetTwo = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), CREATURES_YOU_CONTROL), "target creature you don't control"));

		// Target creature you control fights target creature you don't control.
		this.addEffect(fight(Union.instance(targetOne, targetTwo), "Target creature you control fights target creature you don't control."));
	}
}
