package org.rnd.jmagic.cardTemplates;

import org.rnd.jmagic.engine.*;

public abstract class Signet extends Card
{
	public static final class TapForColored extends org.rnd.jmagic.abilities.TapForMana
	{
		protected char mana1;
		protected char mana2;

		public TapForColored(GameState state, char mana1, char mana2)
		{
			super(state, "(" + mana1 + ")(" + mana2 + ")");
			this.mana1 = mana1;
			this.mana2 = mana2;
			this.setManaCost(new ManaPool("1"));
			this.setName("(1), (T): Add (" + mana1 + ")(" + mana2 + ") to your mana pool.");
		}

		@Override
		public TapForColored create(Game game)
		{
			return new TapForColored(game.physicalState, this.mana1, this.mana2);
		}
	}

	public Signet(GameState state, char mana1, char mana2)
	{
		super(state);

		this.addAbility(new TapForColored(state, mana1, mana2));
	}
}
