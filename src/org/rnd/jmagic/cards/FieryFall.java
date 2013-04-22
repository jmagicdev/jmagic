package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fiery Fall")
@Types({Type.INSTANT})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FieryFall extends Card
{
	public FieryFall(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(spellDealDamage(5, targetedBy(target), "Fiery Fall deals 5 damage to target creature."));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.BasicLandCycling(state, "(1)(R)"));
	}
}
