package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Soul's Attendant")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SoulsAttendant extends Card
{
	public static final class SoulsAttendantAbility0 extends EventTriggeredAbility
	{
		public SoulsAttendantAbility0(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield, you may gain 1 life.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS), false));
			this.addEffect(youMay(gainLife(You.instance(), 1, "You gain 1 life."), "You may gain 1 life."));
		}
	}

	public SoulsAttendant(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever another creature enters the battlefield, you may gain 1
		// life.
		this.addAbility(new SoulsAttendantAbility0(state));
	}
}
