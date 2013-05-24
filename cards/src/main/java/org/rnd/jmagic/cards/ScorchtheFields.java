package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scorch the Fields")
@Types({Type.SORCERY})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ScorchtheFields extends Card
{
	public ScorchtheFields(GameState state)
	{
		super(state);

		// Destroy target land. Scorch the Fields deals 1 damage to each Human
		// creature.
		SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
		this.addEffect(destroy(target, "Destroy target land."));

		this.addEffect(spellDealDamage(1, Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.HUMAN)), "Scorch the Fields deals 1 damage to each Human creature."));
	}
}
