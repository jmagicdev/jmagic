package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;

@Name("Infect")
public final class Infect extends Keyword
{
	public Infect(GameState state)
	{
		super(state, "Infect");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new InfectStatic(this.state));
		return ret;
	}

	public static final class InfectStatic extends StaticAbility
	{
		public InfectStatic(GameState state)
		{
			super(state, "This creature deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters");
		}
	}
}
