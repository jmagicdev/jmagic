package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sage Owl")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class SageOwl extends Card
{
	public static final class Sage extends EventTriggeredAbility
	{
		public Sage(GameState state)
		{
			super(state, "When Sage Owl enters the battlefield, look at the top four cards of your library, then put them back in any order.");

			this.addPattern(whenThisEntersTheBattlefield());

			EventType.ParameterMap lookParameters = new EventType.ParameterMap();
			lookParameters.put(EventType.Parameter.CAUSE, This.instance());
			lookParameters.put(EventType.Parameter.PLAYER, You.instance());
			lookParameters.put(EventType.Parameter.NUMBER, numberGenerator(4));
			this.addEffect(new EventFactory(EventType.LOOK_AND_PUT_BACK, lookParameters, "Look at the top four cards of your library, then put them back in any order."));
		}
	}

	public SageOwl(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new Sage(state));
	}
}
