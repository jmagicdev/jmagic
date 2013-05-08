package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Swelter")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Swelter extends Card
{
	public Swelter(GameState state)
	{
		super(state);

		Target t = this.addTarget(CreaturePermanents.instance(), "two target creatures");
		t.setSingleNumber(numberGenerator(2));
		this.addEffect(spellDealDamage(2, targetedBy(t), "Swelter deals 2 damage to each of two target creatures."));
	}
}
