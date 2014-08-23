package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Indestructible")
public final class Indestructible extends Keyword
{
	public Indestructible(GameState state)
	{
		super(state, "Indestructible");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new IndestructibleStatic(this.state));
		return ret;
	}

	public static final class IndestructibleStatic extends StaticAbility
	{
		public IndestructibleStatic(GameState state)
		{
			super(state, "This permanent can't be destroyed.");
			SimpleEventPattern destroy = new SimpleEventPattern(EventType.DESTROY_ONE_PERMANENT);
			destroy.put(EventType.Parameter.PERMANENT, This.instance());
			
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(destroy));
			this.addEffectPart(part);
		}
	}

}
