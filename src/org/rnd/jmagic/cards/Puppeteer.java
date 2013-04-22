package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Puppeteer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Puppeteer extends Card
{
	public static final class Twiddle extends ActivatedAbility
	{
		public Twiddle(GameState state)
		{
			super(state, "(U), (T): You may tap or untap target creature.");

			this.setManaCost(new ManaPool("U"));

			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(youMay(tapOrUntap(targetedBy(target), "target creature"), "You may tap or untap target creature."));
		}
	}

	public Puppeteer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		this.addAbility(new Twiddle(state));
	}
}
