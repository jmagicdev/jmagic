package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Firestorm")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Firestorm extends Card
{
	public Firestorm(GameState state)
	{
		super(state);

		SetGenerator x = ValueOfX.instance(This.instance());

		// As an additional cost to cast Firestorm, discard X cards.
		EventFactory cost = new EventFactory(EventType.DISCARD_CHOICE, "discard X cards");
		cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
		cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
		cost.parameters.put(EventType.Parameter.NUMBER, x);
		cost.willUseX();
		this.addCost(cost);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "X target creatures and/or players");
		target.setSingleNumber(x);

		// Firestorm deals X damage to each of X target creatures and/or
		// players.
		this.addEffect(spellDealDamage(x, targetedBy(target), "Firestorm deals X damage to each of X target creatures and/or players."));
	}
}
