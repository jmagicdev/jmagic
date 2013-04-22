package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Millstone")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ANTIQUITIES, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Millstone extends Card
{
	public static final class Mill extends ActivatedAbility
	{
		public Mill(GameState state)
		{
			super(state, "(2), (T): Target player puts the top two cards of his or her library into his or her graveyard.");

			this.setManaCost(new ManaPool("2"));

			this.costsTap = true;

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(millCards(targetedBy(target), 2, "Target player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public Millstone(GameState state)
	{
		super(state);

		this.addAbility(new Mill(state));
	}
}
