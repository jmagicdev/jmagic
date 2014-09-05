package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Overrun")
@Types({Type.SORCERY})
@ManaCost("2GGG")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Overrun extends Card
{
	public Overrun(GameState state)
	{
		super(state);

		String effectName = "Creatures you control get +3/+3 and gain trample until end of turn.";
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +3, +3, effectName, org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
