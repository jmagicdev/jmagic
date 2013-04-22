package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Acceptable Losses")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class AcceptableLosses extends Card
{
	public AcceptableLosses(GameState state)
	{
		super(state);

		// Additional cost
		EventType.ParameterMap discardParameters = new EventType.ParameterMap();
		discardParameters.put(EventType.Parameter.CAUSE, This.instance());
		discardParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		discardParameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addCost(new EventFactory(EventType.DISCARD_RANDOM, discardParameters, "discard a card at random"));

		// Effect
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(spellDealDamage(5, targetedBy(target), "Acceptable Losses deals 5 damage to target creature."));
	}
}
