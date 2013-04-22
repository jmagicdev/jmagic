package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Midnight Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MidnightGuard extends Card
{
	public static final class MidnightGuardAbility0 extends EventTriggeredAbility
	{
		public MidnightGuardAbility0(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield, untap Midnight Guard.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS), false));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Midnight Guard."));
		}
	}

	public MidnightGuard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Whenever another creature enters the battlefield, untap Midnight
		// Guard.
		this.addAbility(new MidnightGuardAbility0(state));
	}
}
