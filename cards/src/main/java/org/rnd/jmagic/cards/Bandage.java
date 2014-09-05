package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bandage")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Stronghold.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Bandage extends Card
{
	public Bandage(GameState state)
	{
		super(state);

		// Prevent the next 1 damage that would be dealt to target creature or
		// player this turn.
		Target target = this.addTarget(Union.instance(CreaturePermanents.instance(), Players.instance()), "target creature or player");

		EventFactory prevent = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Prevent the next 1 damage that would be dealt to target creature or player this turn.");
		prevent.parameters.put(EventType.Parameter.CAUSE, This.instance());
		prevent.parameters.put(EventType.Parameter.PREVENT, Identity.instance(targetedBy(target), 1));
		this.addEffect(prevent);

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
