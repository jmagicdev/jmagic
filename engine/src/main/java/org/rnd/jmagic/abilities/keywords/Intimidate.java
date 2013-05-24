package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Intimidate")
public final class Intimidate extends Keyword
{
	public Intimidate(GameState state)
	{
		super(state, "Intimidate");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new IntimidateStatic(this.state));
		return ret;
	}

	public static final class IntimidateStatic extends StaticAbility
	{
		public IntimidateStatic(GameState state)
		{
			super(state, "This creature can't be blocked except by artifact creatures and/or creatures that share a color with it.");

			SetGenerator artifacts = HasType.instance(Type.ARTIFACT);
			SetGenerator sharesColor = HasColor.instance(ColorsOf.instance(This.instance()));
			SetGenerator creatures = HasType.instance(Type.CREATURE);
			SetGenerator cantBlockThis = RelativeComplement.instance(creatures, Union.instance(artifacts, sharesColor));
			SetGenerator illegalBlock = Intersect.instance(Blocking.instance(This.instance()), cantBlockThis);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(illegalBlock));
			this.addEffectPart(part);
		}
	}
}
