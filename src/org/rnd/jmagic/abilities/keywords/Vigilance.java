package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;

@Name("Vigilance")
public final class Vigilance extends Keyword
{
	public Vigilance(GameState state)
	{
		super(state, "Vigilance");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new VigilanceStatic(this.state));
		return ret;
	}

	public static final class VigilanceStatic extends StaticAbility
	{
		public VigilanceStatic(GameState state)
		{
			super(state, "Attacking doesn't cause this creature to tap");
		}
	}
}
