package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public abstract class InvasionLand extends Card
{
	public final static class TapForColored extends ActivatedAbility
	{
		private String mana;

		public TapForColored(GameState state, String mana)
		{
			super(state, "(T): Add (" + mana + ") to your mana pool.");
			this.mana = mana;

			this.costsTap = true;

			this.addEffect(addManaToYourManaPoolFromAbility(mana));
		}

		@Override
		public TapForColored create(Game game)
		{
			return new TapForColored(game.physicalState, this.mana);
		}
	}

	public final static class SacForColors extends ActivatedAbility
	{
		private String thisName;
		private Color[] manaColors;

		public SacForColors(GameState state, String thisName, Color... manaColors)
		{
			super(state, "(T), Sacrifice " + thisName + ": Add " + colorString(manaColors) + " to your mana pool.");

			this.thisName = thisName;
			this.manaColors = manaColors;

			this.costsTap = true;
			this.addCost(sacrificeThis(thisName));

			this.addEffect(addManaToYourManaPoolFromAbility(colorString(manaColors)));
		}

		@Override
		public SacForColors create(Game game)
		{
			return new SacForColors(game.physicalState, this.thisName, this.manaColors);
		}

		private static String colorString(Color[] colors)
		{
			StringBuilder ret = new StringBuilder();
			for(Color c: colors)
				ret.append("(" + c.getLetter() + ")");
			return ret.toString();
		}
	}

	public InvasionLand(GameState state, Color primaryColor)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
		this.addAbility(new TapForColored(state, primaryColor.getLetter()));
		this.addAbility(new SacForColors(state, this.getName(), primaryColor.getAllies()));
	}
}
