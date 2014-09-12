package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.Convenience.CreateTokensFactory;
import org.rnd.jmagic.engine.*;

@Name("Coral Barrier")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class CoralBarrier extends Card
{
	public static final class CoralBarrierAbility1 extends EventTriggeredAbility
	{
		public CoralBarrierAbility1(GameState state)
		{
			super(state, "When Coral Barrier enters the battlefield, put a 1/1 blue Squid creature token with islandwalk onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			CreateTokensFactory squid = new CreateTokensFactory(1, 1, 1, "Put X 1/1 blue Squid creature tokens with islandwalk onto the battlefield, where X is the number of +1/+1 counters on Chasm Skulker.");
			squid.setColors(Color.BLUE);
			squid.setSubTypes(SubType.SQUID);
			squid.addAbility(org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk.class);
			this.addEffect(squid.getEventFactory());
		}
	}

	public CoralBarrier(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// When Coral Barrier enters the battlefield, put a 1/1 blue Squid
		// creature token with islandwalk onto the battlefield. (It can't be
		// blocked as long as defending player controls an Island.)
		this.addAbility(new CoralBarrierAbility1(state));
	}
}
