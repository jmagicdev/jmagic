package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Kavu Climber")
@Types({Type.CREATURE})
@SubTypes({SubType.KAVU})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class KavuClimber extends Card
{
	public static final class SmartKavu extends EventTriggeredAbility
	{
		public SmartKavu(GameState state)
		{
			super(state, "When Kavu Climber enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public KavuClimber(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new SmartKavu(state));
	}
}
