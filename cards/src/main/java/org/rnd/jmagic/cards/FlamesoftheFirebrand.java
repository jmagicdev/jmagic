package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flames of the Firebrand")
@Types({Type.SORCERY})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class FlamesoftheFirebrand extends Card
{
	public FlamesoftheFirebrand(GameState state)
	{
		super(state);

		// Flames of the Firebrand deals 3 damage divided as you choose among
		// one, two, or three target creatures and/or players.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "one, two, or three target creatures and/or players");
		target.setNumber(1, 3);
		this.setDivision(Union.instance(numberGenerator(3), Identity.instance("damage")));

		EventType.ParameterMap damageParameters = new EventType.ParameterMap();
		damageParameters.put(EventType.Parameter.SOURCE, This.instance());
		damageParameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, damageParameters, "Flames of the Firebrand deals 3 damage divided as you choose among one, two, or three target creatures and/or players."));
	}
}
