package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Fodder Launch")
@Types({Type.SORCERY, Type.TRIBAL})
@SubTypes({SubType.GOBLIN})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class FodderLaunch extends Card
{
	public FodderLaunch(GameState state)
	{
		super(state);

		this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.GOBLIN), "sacrifice a Goblin"));

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), -5, -5, "Target creature gets -5/-5 until end of turn."));
		this.addEffect(spellDealDamage(5, ControllerOf.instance(targetedBy(target)), "Fodder Launch deals 5 damage to that creature's controller."));
	}
}
