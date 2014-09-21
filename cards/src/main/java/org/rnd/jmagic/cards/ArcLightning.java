package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arc Lightning")
@Types({Type.SORCERY})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class ArcLightning extends Card
{
	public ArcLightning(GameState state)
	{
		super(state);

		// Arc Lightning deals 3 damage divided as you choose among one, two, or
		// three target creatures and/or players.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "one, two, or three target creatures and/or players").setNumber(1, 3);
		this.setDivision(Union.instance(numberGenerator(2), Identity.instance("damage")));

		EventType.ParameterMap damageParameters = new EventType.ParameterMap();
		damageParameters.put(EventType.Parameter.SOURCE, This.instance());
		damageParameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, damageParameters, "Arc Lightning deals 3 damage divided as you choose among one, two, or three target creatures and/or players."));
	}
}
