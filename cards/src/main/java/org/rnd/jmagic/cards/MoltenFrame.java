package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Molten Frame")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MoltenFrame extends Card
{
	public MoltenFrame(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), CreaturePermanents.instance()), "target artifact creature");

		this.addEffect(destroy(targetedBy(target), "Destroy target artifact creature."));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)"));
	}
}
