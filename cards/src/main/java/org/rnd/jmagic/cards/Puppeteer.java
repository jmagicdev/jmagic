package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Puppeteer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.UNCOMMON)})
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
