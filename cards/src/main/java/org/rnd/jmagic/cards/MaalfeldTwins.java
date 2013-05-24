package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Maalfeld Twins")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("5B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class MaalfeldTwins extends Card
{
	public static final class MaalfeldTwinsAbility0 extends EventTriggeredAbility
	{
		public MaalfeldTwinsAbility0(GameState state)
		{
			super(state, "When Maalfeld Twins dies, put two 2/2 black Zombie creature tokens onto the battlefield.");
			this.addPattern(whenThisDies());

			CreateTokensFactory tokens = new CreateTokensFactory(2, 2, 2, "Put two 2/2 black Zombie creature tokens onto the battlefield.");
			tokens.setColors(Color.BLACK);
			tokens.setSubTypes(SubType.ZOMBIE);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public MaalfeldTwins(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Maalfeld Twins dies, put two 2/2 black Zombie creature tokens
		// onto the battlefield.
		this.addAbility(new MaalfeldTwinsAbility0(state));
	}
}
