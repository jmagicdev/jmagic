package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Centaur Healer")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.CLERIC})
@ManaCost("1GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class CentaurHealer extends Card
{
	public static final class CentaurHealerAbility0 extends EventTriggeredAbility
	{
		public CentaurHealerAbility0(GameState state)
		{
			super(state, "When Centaur Healer enters the battlefield, you gain 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public CentaurHealer(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Centaur Healer enters the battlefield, you gain 3 life.
		this.addAbility(new CentaurHealerAbility0(state));
	}
}
