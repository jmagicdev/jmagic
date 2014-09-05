package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ichor Explosion")
@Types({Type.SORCERY})
@ManaCost("5BB")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class IchorExplosion extends Card
{
	public IchorExplosion(GameState state)
	{
		super(state);

		// As an additional cost to cast Ichor Explosion, sacrifice a creature.
		EventFactory sacrifice = sacrifice(You.instance(), 1, CreaturePermanents.instance(), "sacrifice a creature");
		this.addCost(sacrifice);

		// All creatures get -X/-X until end of turn, where X is the sacrificed
		// creature's power.
		SetGenerator X = PowerOf.instance(OldObjectOf.instance(CostResult.instance(sacrifice)));
		SetGenerator MinusX = Subtract.instance(numberGenerator(0), X);
		this.addEffect(ptChangeUntilEndOfTurn(CreaturePermanents.instance(), MinusX, MinusX, "All creatures get -X/-X until end of turn, where X is the sacrificed creature's power."));
	}
}
