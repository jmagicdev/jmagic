package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Fuse extends Keyword
{
	public Fuse(GameState state)
	{
		super(state, "Fuse");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<>();
		ret.add(new FuseCastAbility(this.state));
		return ret;
	}

	public static final ContinuousEffectType FUSE = new ContinuousEffectType("FUSE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			parameters.get(Parameter.OBJECT).getOne(GameObject.class).fuseable = true;
		}

		@Override
		public Layer layer()
		{
			return Layer.RULE_CHANGE;
		}
	};

	public static final class FuseCastAbility extends StaticAbility
	{
		public FuseCastAbility(GameState state)
		{
			super(state, "You may cast both one or both halves of this card from your hand.");

			this.canApply = Intersect.instance(This.instance(), InZone.instance(HandOf.instance(You.instance())));

			ContinuousEffect.Part part = new ContinuousEffect.Part(FUSE);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}
}
