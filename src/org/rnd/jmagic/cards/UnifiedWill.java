package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unified Will")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class UnifiedWill extends Card
{
	public UnifiedWill(GameState state)
	{
		super(state);

		// Counter target spell if you control more creatures than that spell's
		// controller.
		Target target = this.addTarget(Spells.instance(), "target spell");

		SetGenerator yours = Count.instance(CREATURES_YOU_CONTROL);
		SetGenerator theirs = Count.instance(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(ControllerOf.instance(targetedBy(target)))));
		SetGenerator difference = Subtract.instance(yours, theirs);
		SetGenerator condition = Intersect.instance(difference, Between.instance(1, null));

		EventFactory counter = counter(targetedBy(target), "Counter target spell");

		EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Counter target spell if you control more creatures than that spell's controller.");
		effect.parameters.put(EventType.Parameter.IF, condition);
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(counter));
		this.addEffect(effect);
	}
}
