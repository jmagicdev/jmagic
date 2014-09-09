package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thragtusk")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class Thragtusk extends Card
{
	public static final class ThragtuskAbility0 extends EventTriggeredAbility
	{
		public ThragtuskAbility0(GameState state)
		{
			super(state, "When Thragtusk enters the battlefield, you gain 5 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 5, "You gain 5 life."));
		}
	}

	public static final class ThragtuskAbility1 extends EventTriggeredAbility
	{
		public ThragtuskAbility1(GameState state)
		{
			super(state, "When Thragtusk leaves the battlefield, put a 3/3 green Beast creature token onto the battlefield.");
			this.addPattern(whenThisLeavesTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Beast creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.BEAST);
			this.addEffect(token.getEventFactory());
		}
	}

	public Thragtusk(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// When Thragtusk enters the battlefield, you gain 5 life.
		this.addAbility(new ThragtuskAbility0(state));

		// When Thragtusk leaves the battlefield, put a 3/3 green Beast creature
		// token onto the battlefield.
		this.addAbility(new ThragtuskAbility1(state));
	}
}
