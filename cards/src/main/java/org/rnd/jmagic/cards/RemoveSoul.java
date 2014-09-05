package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Remove Soul")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Legends.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class RemoveSoul extends Card
{
	public RemoveSoul(GameState state)
	{
		super(state);

		SetGenerator creatureSpells = Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		Target target = this.addTarget(creatureSpells, "target creature spell");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target creature spell."));
	}
}
