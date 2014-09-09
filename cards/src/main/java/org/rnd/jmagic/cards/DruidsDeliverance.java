package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Druid's Deliverance")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class DruidsDeliverance extends Card
{
	public DruidsDeliverance(GameState state)
	{
		super(state);

		// Prevent all combat damage that would be dealt to you this turn.
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(new PreventAllTo(state.game, You.instance(), "Prevent all combat damage that would be dealt to equipped creature", true)));
		this.addEffect(createFloatingEffect("Prevent all combat damage that would be dealt to you this turn.", part));

		// Populate.
		this.addEffect(populate());
	}
}
