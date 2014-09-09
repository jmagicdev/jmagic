package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vampire Lacerator")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VAMPIRE})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class VampireLacerator extends Card
{
	public static final class VampireBleed extends EventTriggeredAbility
	{
		public VampireBleed(GameState state)
		{
			super(state, "At the beginning of your upkeep, you lose 1 life unless an opponent has 10 or less life.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator lifeTotals = LifeTotalOf.instance(OpponentsOf.instance(You.instance()));
			SetGenerator opponentHasTenOrLess = Intersect.instance(lifeTotals, Between.instance(null, 10));

			EventFactory loseFactory = loseLife(You.instance(), 1, "You lose 1 life");

			EventFactory ifFactory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "You lose 1 life unless an opponent has 10 or less life");
			ifFactory.parameters.put(EventType.Parameter.IF, opponentHasTenOrLess);
			ifFactory.parameters.put(EventType.Parameter.ELSE, Identity.instance(loseFactory));
			this.addEffect(ifFactory);
		}
	}

	public VampireLacerator(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new VampireBleed(state));
	}
}
