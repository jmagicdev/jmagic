package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Venerable Monk")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MONK, SubType.CLERIC})
@ManaCost("2W")
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
