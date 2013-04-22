package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Matca Rioters")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class MatcaRioters extends Card
{
	public static final class DomainPT extends CharacteristicDefiningAbility
	{
		public DomainPT(GameState state)
		{
			super(state, "Matca Rioters's power and toughness are each equal to the number of basic land types among lands you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Domain.instance(You.instance());

			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public MatcaRioters(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new DomainPT(state));
	}
}
