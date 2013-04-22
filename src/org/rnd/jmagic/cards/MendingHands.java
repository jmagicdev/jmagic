package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mending Hands")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.BETRAYERS_OF_KAMIGAWA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MendingHands extends Card
{
	public MendingHands(GameState state)
	{
		super(state);

		Target target = this.addTarget(Union.instance(CreaturePermanents.instance(), Players.instance()), "target creature or player");

		EventFactory prevent = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Prevent the next 4 damage that would be dealt to target creature or player this turn.");
		prevent.parameters.put(EventType.Parameter.CAUSE, This.instance());
		prevent.parameters.put(EventType.Parameter.PREVENT, Identity.instance(targetedBy(target), 4));
		this.addEffect(prevent);
	}
}
