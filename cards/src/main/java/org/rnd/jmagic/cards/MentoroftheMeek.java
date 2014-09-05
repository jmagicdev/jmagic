package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mentor of the Meek")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class MentoroftheMeek extends Card
{
	public static final class MentoroftheMeekAbility0 extends EventTriggeredAbility
	{
		public MentoroftheMeekAbility0(GameState state)
		{
			super(state, "Whenever another creature with power 2 or less enters the battlefield under your control, you may pay (1). If you do, draw a card.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasPower.instance(Between.instance(null, 2)), ABILITY_SOURCE_OF_THIS), false));
			this.addEffect(ifThen(youMayPay("(1)"), drawACard(), "You may pay (1). If you do, draw a card."));
		}
	}

	public MentoroftheMeek(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever another creature with power 2 or less enters the battlefield
		// under your control, you may pay (1). If you do, draw a card.
		this.addAbility(new MentoroftheMeekAbility0(state));
	}
}
