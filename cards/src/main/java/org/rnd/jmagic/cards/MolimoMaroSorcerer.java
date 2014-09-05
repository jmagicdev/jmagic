package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Molimo, Maro-Sorcerer")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4GGG")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Invasion.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MolimoMaroSorcerer extends Card
{
	public static final class MolimoCDA extends CharacteristicDefiningAbility
	{
		public MolimoCDA(GameState state)
		{
			super(state, "Molimo, Maro-Sorcerer's power and toughness are each equal to the number of lands you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator numLands = Count.instance(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(ControllerOf.instance(This.instance()))));

			this.addEffectPart(setPowerAndToughness(This.instance(), numLands, numLands));
		}
	}

	public MolimoMaroSorcerer(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new MolimoCDA(state));
	}
}
