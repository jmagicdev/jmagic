package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Slag Fiend")
@Types({Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class SlagFiend extends Card
{
	public static final class SlagFiendAbility0 extends CharacteristicDefiningAbility
	{
		public SlagFiendAbility0(GameState state)
		{
			super(state, "Slag Fiend's power and toughness are each equal to the number of artifact cards in all graveyards.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator artifacts = HasType.instance(Type.ARTIFACT);
			SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator num = Count.instance(Intersect.instance(artifacts, inGraveyards));
			this.addEffectPart(modifyPowerAndToughness(This.instance(), num, num));
		}
	}

	public SlagFiend(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Slag Fiend's power and toughness are each equal to the number of
		// artifact cards in all graveyards.
		this.addAbility(new SlagFiendAbility0(state));
	}
}
