package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;

@Name("Wither")
public final class Wither extends Keyword
{
	public Wither(GameState state)
	{
		super(state, "Wither");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new WitherStatic(this.state));
		return ret;
	}

	public static final class WitherStatic extends StaticAbility
	{
		public WitherStatic(GameState state)
		{
			super(state, "This deals damage to creatures in the form of -1/-1 counters");
		}
	}
}
