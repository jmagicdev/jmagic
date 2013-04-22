package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 702.84. Annihilator
 * 
 * 702.84a Annihilator is a triggered ability. "Annihilator N" means
 * "Whenever this creature attacks, defending player sacrifices N permanents."
 * 
 * 702.84b If a creature has multiple instances of annihilator, each triggers
 * separately.
 */
@Name("Annihilator")
public abstract class Annihilator extends Keyword
{
	protected int number;

	public Annihilator(GameState state, int number)
	{
		super(state, "Annihilator " + number);
		this.number = number;
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new AnnihilatorAbility(this.state, this.number));
		return ret;
	}

	public static final class AnnihilatorAbility extends EventTriggeredAbility
	{
		private int number;

		public AnnihilatorAbility(GameState state, int number)
		{
			super(state, "Whenever this creature attacks, defending player sacrifices " + org.rnd.util.NumberNames.get(number, "a") + " permanent" + (number == 1 ? "" : "s") + ".");
			this.number = number;

			this.addPattern(whenThisAttacks());
			this.addEffect(sacrifice(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS), number, Permanents.instance(), "Defending player sacrifices " + org.rnd.util.NumberNames.get(number, "a") + " permanent" + (number == 1 ? "" : "s")));
		}

		@Override
		public AnnihilatorAbility create(Game game)
		{
			return new AnnihilatorAbility(game.physicalState, this.number);
		}
	}

	public static final class Final extends Annihilator
	{
		public Final(GameState state, int number)
		{
			super(state, number);
		}

		@Override
		public Final create(Game game)
		{
			return new Final(game.physicalState, this.number);
		}
	}
}
