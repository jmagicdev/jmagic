package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shroud")
public final class Shroud extends Keyword
{
	public Shroud(GameState state)
	{
		super(state, "Shroud");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new ShroudAbility(this.state));
		return ret;
	}

	public static final class ShroudAbility extends StaticAbility
	{
		public ShroudAbility(GameState state)
		{
			super(state, "This permanent or player can't be the target of spells or abilities.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_THE_TARGET_OF);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(SetPattern.EVERYTHING));
			this.addEffectPart(part);
		}
	}
}
