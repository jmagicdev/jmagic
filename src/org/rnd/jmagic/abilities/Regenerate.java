package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

public abstract class Regenerate extends ActivatedAbility
{
	public Regenerate(GameState state, String manaString, String permanentName)
	{
		super(state, manaString + ": Regenerate " + permanentName + ".");
		this.setManaCost(new ManaPool(manaString));
		this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate " + permanentName + "."));
	}

	public static final class Final extends Regenerate
	{
		private final String manaString;
		private final String permanentName;

		public Final(GameState state, String manaString, String permanentName)
		{
			super(state, manaString, permanentName);

			this.manaString = manaString;
			this.permanentName = permanentName;
		}

		@Override
		public Final create(Game game)
		{
			return new Final(game.physicalState, this.manaString, this.permanentName);
		}
	}

}
