package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Frost Breath")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class FrostBreath extends Card
{
	public FrostBreath(GameState state)
	{
		super(state);

		// Tap up to two target creatures. Those creatures don't untap during
		// their controller's next untap step.
		Target target = this.addTarget(CreaturePermanents.instance(), "up to two target creatures");
		target.setNumber(0, 2);

		EventFactory tap = new EventFactory(EventType.TAP_HARD, "Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.");
		tap.parameters.put(EventType.Parameter.CAUSE, This.instance());
		tap.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(tap);
	}
}
