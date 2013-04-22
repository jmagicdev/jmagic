package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Overrun")
@Types({Type.SORCERY})
@ManaCost("2GGG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.UNCOMMON)})
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
