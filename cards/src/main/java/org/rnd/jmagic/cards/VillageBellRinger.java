package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Village Bell-Ringer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class VillageBellRinger extends Card
{
	public static final class VillageBellRingerAbility1 extends EventTriggeredAbility
	{
		public VillageBellRingerAbility1(GameState state)
		{
			super(state, "When Village Bell-Ringer enters the battlefield, untap all creatures you control.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(untap(CREATURES_YOU_CONTROL, "Untap all creatures you control."));
		}
	}

	public VillageBellRinger(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// When Village Bell-Ringer enters the battlefield, untap all creatures
		// you control.
		this.addAbility(new VillageBellRingerAbility1(state));
	}
}
