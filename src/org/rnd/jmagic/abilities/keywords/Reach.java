package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;

@Name("Reach")
public final class Reach extends Keyword
{
	public Reach(GameState state)
	{
		super(state, "Reach");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new ReachStatic(this.state));
		return ret;
	}

	public static final class ReachStatic extends StaticAbility
	{
		public ReachStatic(GameState state)
		{
			super(state, "This creature can block creatures with flying");
		}
	}
}
