package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soulless One")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.AVATAR})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class SoullessOne extends Card
{
	public static final class ZombiePT extends CharacteristicDefiningAbility
	{
		public ZombiePT(GameState state)
		{
			super(state, "Soulless One's power and toughness are each equal to the number of Zombies on the battlefield plus the number of Zombie cards in all graveyards.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator aliveAndDead = InZone.instance(Union.instance(Battlefield.instance(), GraveyardOf.instance(Players.instance())));
			SetGenerator zombiesAndDeadZombies = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), aliveAndDead);
			SetGenerator number = Count.instance(zombiesAndDeadZombies);

			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public SoullessOne(GameState state)
	{
		super(state);

		// Soulless One's power and toughness are each equal to the number of
		// Zombies on the battlefield plus the number of Zombie cards in all
		// graveyards.
		this.addAbility(new ZombiePT(state));
	}
}
