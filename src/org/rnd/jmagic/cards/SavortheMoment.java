package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Savor the Moment")
@Types({Type.SORCERY})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SavortheMoment extends Card
{
	public SavortheMoment(GameState state)
	{
		super(state);

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		parameters.put(EventType.Parameter.STEP, Identity.instance(Step.StepType.UNTAP));
		this.addEffect(new EventFactory(EventType.TAKE_EXTRA_TURN, parameters, "Take an extra turn after this one. Skip the untap step of that turn."));
	}
}
