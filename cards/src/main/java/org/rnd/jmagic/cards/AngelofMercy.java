package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Angel of Mercy")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AngelofMercy extends Card
{
	public static final class GainLife extends EventTriggeredAbility
	{
		public GainLife(GameState state)
		{
			super(state, "When Angel of Mercy enters the battlefield, you gain 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public AngelofMercy(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new GainLife(state));
	}
}
