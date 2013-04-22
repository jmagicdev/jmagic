package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Turn to Mist")
@Types({Type.INSTANT})
@ManaCost("1(WU)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class TurntoMist extends Card
{
	public TurntoMist(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		EventType.ParameterMap rfgParameters = new EventType.ParameterMap();
		rfgParameters.put(EventType.Parameter.CAUSE, This.instance());
		rfgParameters.put(EventType.Parameter.TARGET, targetedBy(target));
		this.addEffect(new EventFactory(SLIDE, rfgParameters, "Exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step."));
	}
}
