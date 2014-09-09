package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reverberate")
@Types({Type.INSTANT})
@ManaCost("RR")
@ColorIdentity({Color.RED})
public final class Reverberate extends Card
{
	public Reverberate(GameState state)
	{
		super(state);

		// Copy target instant or sorcery spell. You may choose new targets for
		// the copy.
		Target target = this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), Spells.instance()), "target instant or sorcery spell");

		EventType.ParameterMap copyParameters = new EventType.ParameterMap();
		copyParameters.put(EventType.Parameter.CAUSE, This.instance());
		copyParameters.put(EventType.Parameter.PLAYER, You.instance());
		copyParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(EventType.COPY_SPELL_OR_ABILITY, copyParameters, "Copy target instant or sorcery spell. You may choose new targets for the copy."));

	}
}
