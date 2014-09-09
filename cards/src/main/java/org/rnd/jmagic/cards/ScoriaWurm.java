package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scoria Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class ScoriaWurm extends Card
{
	// At the beginning of your upkeep, flip a coin. If you lose the flip,
	// return Scoria Wurm to its owner's hand.
	public static final class FlipBounce extends EventTriggeredAbility
	{
		public FlipBounce(GameState state)
		{
			super(state, "At the beginning of your upkeep, flip a coin. If you lose the flip, return Scoria Wurm to its owner's hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory flip = new EventFactory(EventType.FLIP_COIN, "Flip a coin.");
			flip.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Flip a coin. If you lose the flip, return Scoria Wurm to its owner's hand.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(flip));
			factory.parameters.put(EventType.Parameter.ELSE, Identity.instance(bounce(ABILITY_SOURCE_OF_THIS, "Return Scoria Wurm to its owner's hand.")));
			this.addEffect(factory);
		}
	}

	public ScoriaWurm(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		this.addAbility(new FlipBounce(state));
	}
}
