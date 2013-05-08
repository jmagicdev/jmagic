package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;

@Name("Haste")
public final class Haste extends Keyword
{
	public Haste(GameState state)
	{
		super(state, "Haste");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> abilities = new java.util.LinkedList<StaticAbility>();
		abilities.add(new HasteStatic(this.state));
		return abilities;
	}

	public static final class HasteStatic extends StaticAbility
	{
		public HasteStatic(GameState state)
		{
			super(state, "This creature can attack or use activated abilities whose cost includes the tap symbol or the untap symbol even if it hasn't been controlled by its controller continuously since the start of his or her most recent turn");
		}
	}

	/** @return True. */
	@Override
	public boolean isHaste()
	{
		return true;
	}
}
