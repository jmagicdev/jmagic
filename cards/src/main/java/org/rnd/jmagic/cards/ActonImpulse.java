package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Act on Impulse")
@Types({Type.SORCERY})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class ActonImpulse extends Card
{
	public ActonImpulse(GameState state)
	{
		super(state);

		// Exile the top three cards of your library.
		EventFactory exile = exile(TopCards.instance(3, LibraryOf.instance(You.instance())), "Exile the top three cards of your library.");

		// Until end of turn, you may play cards exiled this way.
		SetGenerator exiledThisWay = NewObjectOf.instance(EffectResult.instance(exile));
		PlayPermission permission = new PlayPermission(You.instance());

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, exiledThisWay);
		part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(permission));
		this.addEffect(createFloatingEffect("Until end of turn, you may play cards exiled this way.", part));

	}
}
