package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rite of Flame")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Coldsnap.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RiteofFlame extends Card
{
	public RiteofFlame(GameState state)
	{
		super(state);

		{
			this.addEffect(addManaToYourManaPoolFromSpell("(R)(R)", "Add " + "(R)(R)" + " to your mana pool,"));
		}

		{
			EventType.ParameterMap parameters = new EventType.ParameterMap();
			SetGenerator numberOfRitesInGraveyards = Count.instance(Intersect.instance(HasName.instance("Rite of Flame"), InZone.instance(GraveyardOf.instance(Players.instance()))));
			SetGenerator singleRed = Identity.fromCollection(new ManaPool("R"));
			parameters.put(EventType.Parameter.SOURCE, This.instance());
			parameters.put(EventType.Parameter.MANA, singleRed);
			parameters.put(EventType.Parameter.NUMBER, numberOfRitesInGraveyards);
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.ADD_MANA, parameters, "then add (R) to your mana pool for each card named Rite of Flame in all graveyards."));
		}
	}
}
