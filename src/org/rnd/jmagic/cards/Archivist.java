package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Archivist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Archivist extends Card
{
	public static final class ArchivistAbility0 extends ActivatedAbility
	{
		public ArchivistAbility0(GameState state)
		{
			super(state, "(T): Draw a card.");
			this.costsTap = true;
			this.addEffect(drawACard());
		}
	}

	public Archivist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Draw a card.
		this.addAbility(new ArchivistAbility0(state));
	}
}
