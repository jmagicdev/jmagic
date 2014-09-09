package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Garruk's Packleader")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class GarruksPackleader extends Card
{
	public static final class GarruksPackleaderAbility0 extends EventTriggeredAbility
	{
		public GarruksPackleaderAbility0(GameState state)
		{
			super(state, "Whenever another creature with power 3 or greater enters the battlefield under your control, you may draw a card.");

			SetGenerator otherBigGuys = RelativeComplement.instance(HasPower.instance(Between.instance(3, null)), ABILITY_SOURCE_OF_THIS);
			ZoneChangePattern pattern = new SimpleZoneChangePattern(null, Battlefield.instance(), otherBigGuys, You.instance(), false);
			this.addPattern(pattern);

			this.addEffect(youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card."));
		}
	}

	public GarruksPackleader(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Whenever another creature with power 3 or greater enters the
		// battlefield under your control, you may draw a card.
		this.addAbility(new GarruksPackleaderAbility0(state));
	}
}
