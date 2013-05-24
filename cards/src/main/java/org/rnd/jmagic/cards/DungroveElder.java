package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dungrove Elder")
@Types({Type.CREATURE})
@SubTypes({SubType.TREEFOLK})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class DungroveElder extends Card
{
	public static final class DungroveElderAbility1 extends CharacteristicDefiningAbility
	{
		public DungroveElderAbility1(GameState state)
		{
			super(state, "Dungrove Elder's power and toughness are each equal to the number of Forests you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator x = Count.instance(Intersect.instance(HasSubType.instance(SubType.FOREST), ControlledBy.instance(You.instance())));
			this.addEffectPart(setPowerAndToughness(This.instance(), x, x));
		}
	}

	public DungroveElder(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Dungrove Elder's power and toughness are each equal to the number of
		// Forests you control.
		this.addAbility(new DungroveElderAbility1(state));
	}
}
