package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Horsemanship")
public final class Horsemanship extends Keyword
{
	public Horsemanship(GameState state)
	{
		super(state, "Horsemanship");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new HorsemanshipStatic(this.state));
		return ret;
	}

	public static final class HorsemanshipStatic extends StaticAbility
	{
		public HorsemanshipStatic(GameState state)
		{
			super(state, "This creature can't be blocked except by creatures with horsemanship.");

			SetGenerator blockingWithoutHorsemanship = RelativeComplement.instance(Blocking.instance(This.instance()), HasKeywordAbility.instance(Flying.class));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingWithoutHorsemanship));
			this.addEffectPart(part);
		}
	}
}
