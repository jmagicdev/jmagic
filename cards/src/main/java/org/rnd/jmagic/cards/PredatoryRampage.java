package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Predatory Rampage")
@Types({Type.SORCERY})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class PredatoryRampage extends Card
{
	public PredatoryRampage(GameState state)
	{
		super(state);

		// Creatures you control get +3/+3 until end of turn. Each creature your
		// opponents control blocks this turn if able.
		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +3, +3, "Creatures you control get +3/+3 until end of turn."));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
		part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))));

		this.addEffect(createFloatingEffect("Each creature your opponents control blocks this turn if able.", part));
	}
}
