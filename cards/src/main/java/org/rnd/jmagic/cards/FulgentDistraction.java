package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fulgent Distraction")
@Types({Type.INSTANT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class FulgentDistraction extends Card
{
	public FulgentDistraction(GameState state)
	{
		super(state);

		// Choose two target creatures. Tap those creatures, then unattach all
		// Equipment from them.
		Target target = this.addTarget(CreaturePermanents.instance(), "two target creatures");
		target.setNumber(2, 2);

		SetGenerator targetGenerator = targetedBy(target);

		this.addEffect(tap(targetGenerator, "Tap two target creatures."));

		EventFactory factory = new EventFactory(EventType.UNATTACH, "Unattach all Equipment from them.");
		factory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(AttachedTo.instance(targetGenerator), HasSubType.instance(SubType.EQUIPMENT)));
		this.addEffect(factory);
	}
}
