package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Deathmark")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Coldsnap.class, r = Rarity.UNCOMMON)})
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
