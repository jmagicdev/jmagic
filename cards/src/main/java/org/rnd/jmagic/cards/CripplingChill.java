package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Crippling Chill")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CripplingChill extends Card
{
	public CripplingChill(GameState state)
	{
		super(state);

		// Tap target creature. It doesn't untap during its controller's next
		// untap step.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		EventFactory factory = new EventFactory(EventType.TAP_HARD, "Tap target creature. It doesn't untap during its controller's next untap step.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(factory);

		// Draw a card.
		this.addEffect(drawACard());
	}
}
