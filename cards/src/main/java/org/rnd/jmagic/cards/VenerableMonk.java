package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Venerable Monk")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MONK, SubType.CLERIC})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter2000.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Stronghold.class, r = Rarity.COMMON), @Printings.Printed(ex = Portal.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class VenerableMonk extends Card
{
	public static final class GainTwoLife extends EventTriggeredAbility
	{
		public GainTwoLife(GameState state)
		{
			super(state, "When Venerable Monk enters the battlefield, you gain 2 life.");

			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public VenerableMonk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new GainTwoLife(state));
	}
}
