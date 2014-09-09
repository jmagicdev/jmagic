package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Nylea's Disciple")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.ARCHER})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class NyleasDisciple extends Card
{
	public static class ETBGainLife extends EventTriggeredAbility
	{
		public ETBGainLife(GameState state)
		{
			super(state, "When Nylea's Disciple enters the battlefield, you gain life equal to your devotion to green.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), DevotionTo.instance(Color.GREEN), "You gain life equal to your devotion to green."));
		}
	}

	public NyleasDisciple(GameState state)
	{
		super(state);

		this.addAbility(new ETBGainLife(state));

		this.setPower(3);
		this.setToughness(3);
	}
}
