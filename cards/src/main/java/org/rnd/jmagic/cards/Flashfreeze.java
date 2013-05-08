package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flashfreeze")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.UNCOMMON)})
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
