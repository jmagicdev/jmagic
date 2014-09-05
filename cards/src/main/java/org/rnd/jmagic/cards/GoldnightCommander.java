package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Goldnight Commander")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.CLERIC, SubType.HUMAN})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class GoldnightCommander extends Card
{
	public static final class GoldnightCommanderAbility0 extends EventTriggeredAbility
	{
		public GoldnightCommanderAbility0(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield under your control, creatures you control get +1/+1 until end of turn.");

			SetGenerator otherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, Battlefield.instance(), otherCreatures, You.instance(), false));

			this.addEffect(createFloatingEffect("Creatures you control get +1/+1 until end of turn.", modifyPowerAndToughness(CREATURES_YOU_CONTROL, +1, +1)));
		}
	}

	public GoldnightCommander(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever another creature enters the battlefield under your control,
		// creatures you control get +1/+1 until end of turn.
		this.addAbility(new GoldnightCommanderAbility0(state));
	}
}
