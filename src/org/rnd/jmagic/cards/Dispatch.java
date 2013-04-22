package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dispatch")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Dispatch extends Card
{
	public Dispatch(GameState state)
	{
		super(state);

		// Tap target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(tap(target, "Tap target creature."));

		// Metalcraft \u2014 If you control three or more artifacts, exile that
		// creature.
		EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If you control three or more artifacts, exile that creature.");
		effect.parameters.put(EventType.Parameter.IF, Metalcraft.instance());
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(exile(target, "Exile that creature.")));
		this.addEffect(effect);
	}
}
