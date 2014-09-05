package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Shunt")
@Types({Type.INSTANT})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Darksteel.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Shunt extends Card
{
	public Shunt(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(Spells.instance(), HasOneTarget.instance()), "target spell with a single target");

		EventFactory swerve = new EventFactory(EventType.CHANGE_TARGETS, "Change the target of target spell with a single target.");
		swerve.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		swerve.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(swerve);
	}
}
