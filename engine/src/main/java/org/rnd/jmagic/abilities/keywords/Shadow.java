package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shadow")
public final class Shadow extends Keyword
{
	public Shadow(GameState state)
	{
		super(state, "Shadow");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new ShadowStatic(this.state));
		return ret;
	}

	public static final class ShadowStatic extends StaticAbility
	{
		public ShadowStatic(GameState state)
		{
			super(state, "This creature can block or be blocked by only creatures with shadow.");

			SetGenerator hasShadow = HasKeywordAbility.instance(Shadow.class);
			SetGenerator blockersWithoutShadow = RelativeComplement.instance(Blocking.instance(This.instance()), hasShadow);
			SetGenerator attackersWithoutShadow = RelativeComplement.instance(BlockedBy.instance(This.instance()), hasShadow);

			ContinuousEffect.Part attackingPart = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			attackingPart.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockersWithoutShadow));
			this.addEffectPart(attackingPart);

			ContinuousEffect.Part blockingPart = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			blockingPart.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(attackersWithoutShadow));
			this.addEffectPart(blockingPart);
		}
	}
}
