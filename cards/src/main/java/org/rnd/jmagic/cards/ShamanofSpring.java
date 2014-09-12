package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Shaman of Spring")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.ELF})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class ShamanofSpring extends Card
{
	public static final class ShamanofSpringAbility0 extends EventTriggeredAbility
	{
		public ShamanofSpringAbility0(GameState state)
		{
			super(state, "When Shaman of Spring enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public ShamanofSpring(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Shaman of Spring enters the battlefield, draw a card.
		this.addAbility(new ShamanofSpringAbility0(state));
	}
}
