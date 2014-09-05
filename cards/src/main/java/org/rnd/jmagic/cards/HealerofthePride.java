package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Healer of the Pride")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.CLERIC})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class HealerofthePride extends Card
{
	public static final class HealerofthePrideAbility0 extends EventTriggeredAbility
	{
		public HealerofthePrideAbility0(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield under your control, you gain 2 life.");

			SetGenerator otherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), otherCreatures, You.instance(), false));

			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public HealerofthePride(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Whenever another creature enters the battlefield under your control,
		// you gain 2 life.
		this.addAbility(new HealerofthePrideAbility0(state));
	}
}
