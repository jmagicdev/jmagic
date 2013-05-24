package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flash")
public final class Flash extends Keyword
{
	public Flash(GameState state)
	{
		super(state, "Flash");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new FlashAbility(this.state));
		return ret;
	}

	public static final class FlashAbility extends StaticAbility
	{
		public FlashAbility(GameState state)
		{
			super(state, "You may play this card any time you could play an instant.");

			SetGenerator youHavePriority = Intersect.instance(You.instance(), PlayerWithPriority.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_TIMING);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(youHavePriority)));
			this.addEffectPart(part);

			this.canApply = NonEmpty.instance();
		}
	}
}
