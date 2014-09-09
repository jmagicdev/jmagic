package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Boneyard Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class BoneyardWurm extends Card
{
	public static final class BoneyardWurmAbility0 extends CharacteristicDefiningAbility
	{
		public BoneyardWurmAbility0(GameState state)
		{
			super(state, "Boneyard Wurm's power and toughness are each equal to the number of creature cards in your graveyard.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Count.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))));
			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public BoneyardWurm(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Boneyard Wurm's power and toughness are each equal to the number of
		// creature cards in your graveyard.
		this.addAbility(new BoneyardWurmAbility0(state));
	}
}
