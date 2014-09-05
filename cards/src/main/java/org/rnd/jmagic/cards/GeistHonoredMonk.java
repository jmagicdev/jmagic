package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Geist-Honored Monk")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MONK})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class GeistHonoredMonk extends Card
{
	public static final class GeistHonoredMonkAbility1 extends CharacteristicDefiningAbility
	{
		public GeistHonoredMonkAbility1(GameState state)
		{
			super(state, "Geist-Honored Monk's power and toughness are each equal to the number of creatures you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Count.instance(CREATURES_YOU_CONTROL);
			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public static final class GeistHonoredMonkAbility2 extends EventTriggeredAbility
	{
		public GeistHonoredMonkAbility2(GameState state)
		{
			super(state, "When Geist-Honored Monk enters the battlefield, put two 1/1 white Spirit creature tokens with flying onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory factory = new CreateTokensFactory(2, 1, 1, "Put two 1/1 white Spirit creature tokens with flying onto the battlefield.");
			factory.setColors(Color.WHITE);
			factory.setSubTypes(SubType.SPIRIT);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public GeistHonoredMonk(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Geist-Honored Monk's power and toughness are each equal to the number
		// of creatures you control.
		this.addAbility(new GeistHonoredMonkAbility1(state));

		// When Geist-Honored Monk enters the battlefield, put two 1/1 white
		// Spirit creature tokens with flying onto the battlefield.
		this.addAbility(new GeistHonoredMonkAbility2(state));
	}
}
