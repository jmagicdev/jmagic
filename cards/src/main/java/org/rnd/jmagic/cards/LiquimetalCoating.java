package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Liquimetal Coating")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class LiquimetalCoating extends Card
{
	public static final class LiquimetalCoatingAbility0 extends ActivatedAbility
	{
		public LiquimetalCoatingAbility0(GameState state)
		{
			super(state, "(T): Target permanent becomes an artifact in addition to its other types until end of turn.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.ARTIFACT));
			this.addEffect(createFloatingEffect("Target permanent becomes an artifact in addition to its other types until end of turn.", part));
		}
	}

	public LiquimetalCoating(GameState state)
	{
		super(state);

		// (T): Target permanent becomes an artifact in addition to its other
		// types until end of turn.
		this.addAbility(new LiquimetalCoatingAbility0(state));
	}
}
