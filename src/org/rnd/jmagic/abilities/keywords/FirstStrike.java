package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;

@Name("First strike")
public final class FirstStrike extends Keyword
{
	public FirstStrike(GameState state)
	{
		super(state, "First strike");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new FirstStrikeStatic(this.state));
		return ret;
	}

	public static final class FirstStrikeStatic extends StaticAbility
	{
		public FirstStrikeStatic(GameState state)
		{
			super(state, "This creature deals combat damage before creatures without first strike");
		}
	}
}
