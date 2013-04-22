package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deathmark")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Deathmark extends Card
{
	public Deathmark(GameState state)
	{
		super(state);

		SetGenerator targetable = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.WHITE, Color.GREEN));
		Target target = this.addTarget(targetable, "target green or white creature");
		this.addEffect(destroy(targetedBy(target), "Destroy target green or white creature."));
	}
}
