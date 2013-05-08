package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flying")
public final class Flying extends Keyword
{
	public Flying(GameState state)
	{
		super(state, "Flying");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new FlyingStatic(this.state));
		return ret;
	}

	public static final class FlyingStatic extends StaticAbility
	{
		public FlyingStatic(GameState state)
		{
			super(state, "This can't be blocked except by creatures with flying or reach.");

			SetGenerator hasFlyingOrReach = Union.instance(HasKeywordAbility.instance(Flying.class), HasKeywordAbility.instance(Reach.class));
			SetGenerator notBlockingWithFlyingOrReach = RelativeComplement.instance(Blocking.instance(This.instance()), hasFlyingOrReach);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(notBlockingWithFlyingOrReach));
			this.addEffectPart(part);
		}
	}

}
