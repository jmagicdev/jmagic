package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Forked Bolt")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ForkedBolt extends Card
{
	public ForkedBolt(GameState state)
	{
		super(state);

		// Forked Bolt deals 2 damage divided as you choose among one or two
		// target creatures and/or players.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "one or two target creatures and/or players");
		target.setNumber(1, 2);
		this.setDivision(Union.instance(numberGenerator(2), Identity.instance("damage")));

		EventType.ParameterMap damageParameters = new EventType.ParameterMap();
		damageParameters.put(EventType.Parameter.SOURCE, This.instance());
		damageParameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, damageParameters, "Forked Bolt deals 2 damage divided as you choose among one or two target creatures and/or players."));

	}
}
