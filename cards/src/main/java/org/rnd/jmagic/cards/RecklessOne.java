package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reckless One")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.AVATAR})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RecklessOne extends Card
{
	public static final class RecklessOneAbility1 extends CharacteristicDefiningAbility
	{
		public RecklessOneAbility1(GameState state)
		{
			super(state, "Reckless One's power and toughness are each equal to the number of Goblins on the battlefield.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator goblins = Intersect.instance(HasSubType.instance(SubType.GOBLIN), InZone.instance(Battlefield.instance()));
			SetGenerator numGoblins = Count.instance(goblins);
			this.addEffectPart(setPowerAndToughness(This.instance(), numGoblins, numGoblins));
		}
	}

	public RecklessOne(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Reckless One's power and toughness are each equal to the number of
		// Goblins on the battlefield.
		this.addAbility(new RecklessOneAbility1(state));
	}
}
