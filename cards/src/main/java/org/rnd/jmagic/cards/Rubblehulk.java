package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rubblehulk")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4RG")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class Rubblehulk extends Card
{
	public static final class RubblehulkAbility0 extends CharacteristicDefiningAbility
	{
		public RubblehulkAbility0(GameState state)
		{
			super(state, "Rubblehulk's power and toughness are each equal to the number of lands you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator numLands = Count.instance(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(ControllerOf.instance(This.instance()))));

			this.addEffectPart(setPowerAndToughness(This.instance(), numLands, numLands));
		}
	}

	public Rubblehulk(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Rubblehulk's power and toughness are each equal to the number of
		// lands you control.
		this.addAbility(new RubblehulkAbility0(state));

		// Bloodrush \u2014 (1)(R)(G), Discard Rubblehulk: Target attacking
		// creature gets +X/+X until end of turn, where X is the number of lands
		// you control.
		SetGenerator numLands = Count.instance(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(ControllerOf.instance(This.instance()))));
		this.addAbility(new org.rnd.jmagic.abilities.Bloodrush(state, "(1)(R)(G)", "Rubblehulk", numLands, numLands, "Target attacking creature gets +X/+X until end of turn, where X is the number of lands you control."));
	}
}
