package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Scion of the Wild")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ScionoftheWild extends Card
{
	public static final class ScionCDA extends CharacteristicDefiningAbility
	{
		public ScionCDA(GameState state)
		{
			super(state, "Scion of the Wild's power and toughness are each equal to the number of creatures you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator count = Count.instance(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(ControllerOf.instance(This.instance()))));

			this.addEffectPart(setPowerAndToughness(This.instance(), count, count));
		}
	}

	public ScionoftheWild(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new ScionCDA(state));
	}
}
