package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Incinerate")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class Incinerate extends Card
{
	public Incinerate(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		EventType.ParameterMap damageParameters = new EventType.ParameterMap();
		damageParameters.put(EventType.Parameter.SOURCE, This.instance());
		damageParameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		damageParameters.put(EventType.Parameter.TAKER, targetedBy(target));
		this.addEffect(new EventFactory(EventType.DEAL_DAMAGE_EVENLY_CANT_BE_REGENERATED, damageParameters, "Incinerate deals 3 damage to target creature or player. A creature dealt damage this way can't be regenerated this turn."));
	}
}
