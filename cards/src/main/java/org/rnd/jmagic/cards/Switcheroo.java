package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Switcheroo")
@Types({Type.SORCERY})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Switcheroo extends Card
{
	public Switcheroo(GameState state)
	{
		super(state);

		// Exchange control of two target creatures.
		Target target = this.addTarget(CreaturePermanents.instance(), "two target creatures");
		target.setNumber(2, 2);

		EventFactory factory = new EventFactory(EventType.EXCHANGE_CONTROL, "Exchange control of two target creatures.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(factory);
	}
}
