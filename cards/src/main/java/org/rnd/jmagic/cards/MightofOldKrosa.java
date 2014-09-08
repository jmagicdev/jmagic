package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Might of Old Krosa")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class MightofOldKrosa extends Card
{
	public MightofOldKrosa(GameState state)
	{
		super(state);

		// Target creature gets +2/+2 until end of turn. If you cast this spell
		// during your main phase, that creature gets +4/+4 until end of turn
		// instead.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator yourMainPhase = Intersect.instance(CurrentPhase.instance(), MainPhaseOf.instance(You.instance()));
		SetGenerator pump = IfThenElse.instance(yourMainPhase, numberGenerator(+4), numberGenerator(+2));
		this.addEffect(ptChangeUntilEndOfTurn(target, pump, pump, "Target creature gets +2/+2 until end of turn. If you cast this spell during your main phase, that creature gets +4/+4 until end of turn instead."));
	}
}
