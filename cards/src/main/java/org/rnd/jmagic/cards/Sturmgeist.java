package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sturmgeist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Sturmgeist extends Card
{
	public static final class SturmgeistAbility1 extends CharacteristicDefiningAbility
	{
		public SturmgeistAbility1(GameState state)
		{
			super(state, "Sturmgeist's power and toughness are each equal to the number of cards in your hand.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Count.instance(InZone.instance(HandOf.instance(You.instance())));
			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public static final class SturmgeistAbility2 extends EventTriggeredAbility
	{
		public SturmgeistAbility2(GameState state)
		{
			super(state, "Whenever Sturmgeist deals combat damage to a player, draw a card.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(drawACard());
		}
	}

	public Sturmgeist(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Sturmgeist's power and toughness are each equal to the number of
		// cards in your hand.
		this.addAbility(new SturmgeistAbility1(state));

		// Whenever Sturmgeist deals combat damage to a player, draw a card.
		this.addAbility(new SturmgeistAbility2(state));
	}
}
