package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public final class Outlast extends Keyword
{
	private String manaCostString;

	public Outlast(GameState state, String manaCostString)
	{
		super(state, "Outlast " + manaCostString);

		this.manaCostString = manaCostString;
	}

	@Override
	public Outlast create(Game game)
	{
		return new Outlast(game.physicalState, this.manaCostString);
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.<NonStaticAbility>singletonList(new OutlastAbility(this.state, this.manaCostString));
	}

	public static final class OutlastAbility extends ActivatedAbility
	{
		private String manaCostString;

		public OutlastAbility(GameState state, String manaCostString)
		{
			super(state, manaCostString + ", (T): Put a +1/+1 counter on this creature. Outlast only as a sorcery.");

			this.manaCostString = manaCostString;
			this.setManaCost(new ManaPool(manaCostString));
			this.costsTap = true;

			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "this creature"));

			this.activateOnlyAtSorcerySpeed();
		}

		@Override
		public OutlastAbility create(Game game)
		{
			return new OutlastAbility(game.physicalState, this.manaCostString);
		}
	}
}
