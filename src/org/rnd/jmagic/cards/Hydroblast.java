package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hydroblast")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Hydroblast extends Card
{
	public Hydroblast(GameState state)
	{
		super(state);

		{
			Target target = this.addTarget(1, Spells.instance(), "target spell");
			EventFactory counter = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Counter target spell if it's red");
			counter.parameters.put(EventType.Parameter.IF, Intersect.instance(targetedBy(target), HasColor.instance(Color.RED)));
			counter.parameters.put(EventType.Parameter.THEN, Identity.instance(counter(targetedBy(target), "Counter target spell.")));
			this.addEffect(1, counter);
		}

		{
			Target target = this.addTarget(2, Permanents.instance(), "target permanent");
			EventFactory destroy = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "destroy target permanent if it's red.");
			destroy.parameters.put(EventType.Parameter.IF, Intersect.instance(targetedBy(target), HasColor.instance(Color.RED)));
			destroy.parameters.put(EventType.Parameter.THEN, Identity.instance(destroy(targetedBy(target), "Destroy target permanent.")));
			this.addEffect(2, destroy);
		}
	}
}
