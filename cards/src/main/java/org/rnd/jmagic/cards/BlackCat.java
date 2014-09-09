package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Black Cat")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.ZOMBIE})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class BlackCat extends Card
{
	public static final class BlackCatAbility0 extends EventTriggeredAbility
	{
		public BlackCatAbility0(GameState state)
		{
			super(state, "When Black Cat dies, target opponent discards a card at random.");
			this.addPattern(whenThisDies());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			EventType.ParameterMap discardParameters = new EventType.ParameterMap();
			discardParameters.put(EventType.Parameter.CAUSE, This.instance());
			discardParameters.put(EventType.Parameter.PLAYER, target);
			discardParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addEffect(new EventFactory(EventType.DISCARD_RANDOM, discardParameters, "Target opponent discards a card at random"));
		}
	}

	public BlackCat(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Black Cat dies, target opponent discards a card at random.
		this.addAbility(new BlackCatAbility0(state));
	}
}
