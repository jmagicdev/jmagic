package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Rampage extends Keyword
{
	private final int N;

	public Rampage(GameState state, int N)
	{
		super(state, "Rampage " + N);
		this.N = N;
	}

	@Override
	public Rampage create(Game game)
	{
		return new Rampage(game.physicalState, this.N);
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new RampageAbility(this.state, this.N));
		return ret;
	}

	public static final class RampageAbility extends EventTriggeredAbility
	{
		private final int N;

		public RampageAbility(GameState state, int N)
		{
			super(state, "Whenever this creature becomes blocked, it gets " + (N >= 0 ? "+" : "") + N + "/" + (N >= 0 ? "+" : "") + N + " until end of turn for each creature blocking it beyond the first.");
			this.N = N;

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator blockingThis = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.DEFENDER);
			SetGenerator beyondTheFirst = Subtract.instance(Count.instance(blockingThis), numberGenerator(1));
			SetGenerator plusXForEach = Multiply.instance(numberGenerator(N), beyondTheFirst);
			SetGenerator boost = Maximum.instance(Union.instance(numberGenerator(0), plusXForEach));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, thisCard);
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(thisCard, boost, boost, "It gets " + (N >= 0 ? "+" : "") + N + "/" + (N >= 0 ? "+" : "") + N + " until end of turn for each creature blocking it beyond the first."));
		}

		@Override
		public RampageAbility create(Game game)
		{
			return new RampageAbility(game.physicalState, this.N);
		}
	}
}
