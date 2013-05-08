package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Defender")
public final class Defender extends Keyword
{
	public Defender(GameState state)
	{
		super(state, "Defender");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new DefenderAbility(this.state));
		return ret;
	}

	public static final class DefenderAbility extends StaticAbility
	{
		public DefenderAbility(GameState state)
		{
			super(state, "This creature can't attack.");

			SetGenerator thisIsAttacking = Intersect.instance(Attacking.instance(), This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(thisIsAttacking));
			// ABILITY is a flag to ATTACKING_RESTRICTION that tells it not to
			// apply if the source object can attack as though it didn't have
			// defender
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Empty.instance());
			this.addEffectPart(part);
		}
	}
}
