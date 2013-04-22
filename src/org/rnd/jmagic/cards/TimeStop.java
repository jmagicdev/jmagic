package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Time Stop")
@Types({Type.INSTANT})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class TimeStop extends Card
{
	public TimeStop(GameState state)
	{
		super(state);

		EventType.ParameterMap endTheTurnParameters = new EventType.ParameterMap();
		endTheTurnParameters.put(EventType.Parameter.CAUSE, This.instance());
		this.addEffect(new EventFactory(EventType.END_THE_TURN, endTheTurnParameters, "End the turn."));
	}
}
