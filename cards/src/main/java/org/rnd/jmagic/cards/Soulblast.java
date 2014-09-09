package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soulblast")
@Types({Type.INSTANT})
@ManaCost("3RRR")
@ColorIdentity({Color.RED})
public final class Soulblast extends Card
{
	public Soulblast(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		SetGenerator allCreaturesYouControl = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE));
		EventType.ParameterMap params = new EventType.ParameterMap();
		params.put(EventType.Parameter.CAUSE, This.instance());
		params.put(EventType.Parameter.PLAYER, You.instance());
		params.put(EventType.Parameter.PERMANENT, allCreaturesYouControl);
		EventFactory theCost = new EventFactory(EventType.SACRIFICE_PERMANENTS, params, "sacrifice all creatures you control");
		this.addCost(theCost);

		// TODO : If something says "as an additional cost to play spells,
		// sacrifice a creature", this is going to count creatures sacrificed to
		// that effect.
		SetGenerator amount = Sum.instance(PowerOf.instance(OldObjectOf.instance(CostResult.instance(theCost))));
		this.addEffect(spellDealDamage(amount, targetedBy(target), "Soulblast deals damage to target creature or player equal to the total power of the sacrificed creatures."));
	}
}
