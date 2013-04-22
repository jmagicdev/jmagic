package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Swerve")
@Types({Type.INSTANT})
@ManaCost("UR")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class Swerve extends Card
{
	public Swerve(GameState state)
	{
		super(state);

		// Change the target of target spell with a single target.
		Target target = this.addTarget(Intersect.instance(Spells.instance(), HasOneTarget.instance()), "target spell with a single target");

		EventFactory swerve = new EventFactory(EventType.CHANGE_TARGETS, "Change the target of target spell with a single target.");
		swerve.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		swerve.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(swerve);
	}
}
