package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Biorhythm")
@Types({Type.SORCERY})
@ManaCost("6GG")
@ColorIdentity({Color.GREEN})
public final class Biorhythm extends Card
{
	public Biorhythm(GameState state)
	{
		super(state);

		// Each player's life total becomes the number of creatures he or she
		// controls.

		DynamicEvaluation dynamic = DynamicEvaluation.instance();

		EventFactory life = new EventFactory(EventType.SET_LIFE, "Each player's life total becomes the number of creatures he or she controls.");
		life.parameters.put(EventType.Parameter.CAUSE, This.instance());
		life.parameters.put(EventType.Parameter.NUMBER, Count.instance(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(dynamic))));
		life.parameters.put(EventType.Parameter.PLAYER, dynamic);

		EventFactory foreach = new EventFactory(FOR_EACH_PLAYER, "Each player's life total becomes the number of creatures he or she controls.");
		foreach.parameters.put(EventType.Parameter.TARGET, Identity.instance(dynamic));
		foreach.parameters.put(EventType.Parameter.EFFECT, Identity.instance(life));
		this.addEffect(foreach);
	}
}
