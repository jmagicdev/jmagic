package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Elvish Visionary")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class ElvishVisionary extends Card
{
	public static final class CIPCantrip extends EventTriggeredAbility
	{
		public CIPCantrip(GameState state)
		{
			super(state, "When Elvish Visionary enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public ElvishVisionary(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new CIPCantrip(state));
	}
}
