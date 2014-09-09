package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("An-Havva Township")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class AnHavvaTownship extends org.rnd.jmagic.engine.Card
{
	public static final class TapForG extends org.rnd.jmagic.abilities.TapForMana
	{
		public TapForG(GameState state)
		{
			super(state, "(G)");
			this.setManaCost(new ManaPool("1"));
			this.setName("(1), " + this.getName());
		}
	}

	public static final class TapForROrW extends org.rnd.jmagic.abilities.TapForMana
	{
		public TapForROrW(GameState state)
		{
			super(state, "(RW)");
			this.setManaCost(new ManaPool("2"));
			this.setName("(2), " + this.getName());
		}
	}

	public AnHavvaTownship(GameState state)
	{
		super(state);

		this.addAbility(new TapForG(state));
		this.addAbility(new TapForROrW(state));
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
	}
}
