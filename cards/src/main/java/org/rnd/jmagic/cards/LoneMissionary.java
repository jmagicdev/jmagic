package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lone Missionary")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.MONK})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class LoneMissionary extends Card
{
	public static final class ETBGainLife extends EventTriggeredAbility
	{
		public ETBGainLife(GameState state)
		{
			super(state, "When Lone Missionary enters the battlefield, you gain 4 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
		}
	}

	public LoneMissionary(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Lone Missionary enters the battlefield, you gain 4 life.
		this.addAbility(new ETBGainLife(state));
	}
}
