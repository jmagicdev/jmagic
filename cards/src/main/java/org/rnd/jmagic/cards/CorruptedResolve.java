package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Corrupted Resolve")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class CorruptedResolve extends Card
{
	public CorruptedResolve(GameState state)
	{
		super(state);

		// Counter target spell if its controller is poisoned.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));

		EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Counter target spell if its controller is poisoned.");
		effect.parameters.put(EventType.Parameter.IF, Intersect.instance(ControllerOf.instance(target), Poisoned.instance()));
		effect.parameters.put(EventType.Parameter.IF, Identity.instance(counter(target, "Counter target spell.")));
		this.addEffect(effect);
	}
}
