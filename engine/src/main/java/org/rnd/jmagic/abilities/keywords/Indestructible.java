package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

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
			this.addEffectPart(indestructible(This.instance()));
		}
	}

}
