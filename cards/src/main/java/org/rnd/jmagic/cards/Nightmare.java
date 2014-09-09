package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nightmare")
@Types({Type.CREATURE})
@SubTypes({SubType.HORSE, SubType.NIGHTMARE})
@ManaCost("5B")
@ColorIdentity({Color.BLACK})
public final class Nightmare extends Card
{
	public static final class NightmareCDA extends CharacteristicDefiningAbility
	{
		public NightmareCDA(GameState state)
		{
			super(state, "Nightmare's power and toughness are each equal to the number of Swamps you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator count = Count.instance(Intersect.instance(ControlledBy.instance(ControllerOf.instance(This.instance())), HasSubType.instance(SubType.SWAMP)));

			this.addEffectPart(setPowerAndToughness(This.instance(), count, count));
		}
	}

	public Nightmare(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new NightmareCDA(state));
	}
}
