package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Master of Etherium")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.WIZARD, SubType.VEDALKEN})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MasterofEtherium extends Card
{
	public static final class EtheriumStats extends CharacteristicDefiningAbility
	{
		public EtheriumStats(GameState state)
		{
			super(state, "Master of Etherium's power and toughness are each equal to the number of artifacts you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Count.instance(Intersect.instance(HasType.instance(Type.ARTIFACT), ControlledBy.instance(You.instance())));

			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public static final class EtheriumPump extends StaticAbility
	{
		public EtheriumPump(GameState state)
		{
			super(state, "Other artifact creatures you control get +1/+1.");

			SetGenerator objects = RelativeComplement.instance(Intersect.instance(Intersect.instance(HasType.instance(Type.ARTIFACT), HasType.instance(Type.CREATURE)), ControlledBy.instance(You.instance())), This.instance());

			this.addEffectPart(modifyPowerAndToughness(objects, 1, 1));
		}
	}

	public MasterofEtherium(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new EtheriumStats(state));

		this.addAbility(new EtheriumPump(state));
	}
}
