package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Riddlesmith")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Riddlesmith extends Card
{
	public static final class RiddlesmithAbility0 extends EventTriggeredAbility
	{
		public RiddlesmithAbility0(GameState state)
		{
			super(state, "Whenever you cast an artifact spell, you may draw a card. If you do, discard a card.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(HasType.instance(Type.ARTIFACT));
			this.addPattern(pattern);

			this.addEffect(youMayDrawACardIfYouDoDiscardACard());
		}
	}

	public Riddlesmith(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever you cast an artifact spell, you may draw a card. If you do,
		// discard a card.
		this.addAbility(new RiddlesmithAbility0(state));
	}
}
