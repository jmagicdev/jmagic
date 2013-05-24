package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Argent Mutation")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ArgentMutation extends Card
{
	public ArgentMutation(GameState state)
	{
		super(state);

		// Target permanent becomes an artifact in addition to its other types
		// until end of turn.
		SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
		part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.ARTIFACT));
		this.addEffect(createFloatingEffect("Target permanent becomes an artifact in addition to its other types until end of turn.", part));

		// Draw a card.
		this.addEffect(drawACard());
	}
}
