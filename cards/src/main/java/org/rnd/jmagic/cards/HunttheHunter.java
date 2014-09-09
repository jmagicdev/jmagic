package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hunt the Hunter")
@Types({Type.SORCERY})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class HunttheHunter extends Card
{
	public HunttheHunter(GameState state)
	{
		super(state);

		// Target green creature you control gets +2/+2 until end of turn.
		SetGenerator greenGuys = Intersect.instance(HasColor.instance(Color.GREEN), CreaturePermanents.instance());
		SetGenerator yourGreenGuys = Intersect.instance(greenGuys, ControlledBy.instance(You.instance()));
		SetGenerator yourTarget = targetedBy(this.addTarget(yourGreenGuys, "target green creature you control"));
		this.addEffect(ptChangeUntilEndOfTurn(yourTarget, +2, +2, "Target green creature you control gets +2/+2 until end of turn."));

		// It fights target green creature an opponent controls.
		SetGenerator theirGreenGuys = Intersect.instance(greenGuys, ControlledBy.instance(OpponentsOf.instance(You.instance())));
		SetGenerator theirTarget = targetedBy(this.addTarget(theirGreenGuys, "target green creature an opponent controls"));
		this.addEffect(fight(Union.instance(yourTarget, theirTarget), "It fights target green creature an opponent controls."));
	}
}
