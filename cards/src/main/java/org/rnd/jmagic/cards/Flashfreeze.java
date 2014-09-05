package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Flashfreeze")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Coldsnap.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Flashfreeze extends Card
{
	public Flashfreeze(GameState state)
	{
		super(state);

		SetGenerator redOrGreenSpells = Intersect.instance(Spells.instance(), HasColor.instance(Color.RED, Color.GREEN));

		Target target = this.addTarget(redOrGreenSpells, "target red or green spell");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target red or green spell."));
	}
}
