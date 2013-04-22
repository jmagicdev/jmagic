package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tower Defense")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class TowerDefense extends Card
{
	public TowerDefense(GameState state)
	{
		super(state);

		// Creatures you control get +0/+5 and gain reach until end of turn.
		SetGenerator targets = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance()));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targets, +0, +5, "Creatures you control get +0/+5 and gain reach until end of turn.", org.rnd.jmagic.abilities.keywords.Reach.class));
	}
}
