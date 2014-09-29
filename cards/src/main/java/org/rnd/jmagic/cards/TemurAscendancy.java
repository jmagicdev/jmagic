package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Temur Ascendancy")
@Types({Type.ENCHANTMENT})
@ManaCost("GUR")
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class TemurAscendancy extends Card
{
	public static final class TemurAscendancyAbility0 extends StaticAbility
	{
		public TemurAscendancyAbility0(GameState state)
		{
			super(state, "Creatures you control have haste.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public static final class TemurAscendancyAbility1 extends EventTriggeredAbility
	{
		public TemurAscendancyAbility1(GameState state)
		{
			super(state, "Whenever a creature with power 4 or greater enters the battlefield under your control, you may draw a card.");

			SetGenerator bigThings = HasPower.instance(Between.instance(4, null));
			SimpleZoneChangePattern ferocious = new SimpleZoneChangePattern(null, Battlefield.instance(), bigThings, You.instance(), false);
			this.addPattern(ferocious);

			this.addEffect(youMay(drawACard()));
		}
	}

	public TemurAscendancy(GameState state)
	{
		super(state);

		// Creatures you control have haste.
		this.addAbility(new TemurAscendancyAbility0(state));

		// Whenever a creature with power 4 or greater enters the battlefield
		// under your control, you may draw a card.
		this.addAbility(new TemurAscendancyAbility1(state));
	}
}
