package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Pack Rat")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class PackRat extends Card
{
	public static final class PackRatAbility0 extends CharacteristicDefiningAbility
	{
		public PackRatAbility0(GameState state)
		{
			super(state, "Pack Rat's power and toughness are each equal to the number of Rats you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Count.instance(Intersect.instance(HasSubType.instance(SubType.RAT), CREATURES_YOU_CONTROL));

			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public static final class PackRatAbility1 extends ActivatedAbility
	{
		public PackRatAbility1(GameState state)
		{
			super(state, "(2)(B), Discard a card: Put a token onto the battlefield that's a copy of Pack Rat.");
			this.setManaCost(new ManaPool("(2)(B)"));
			// Discard a card
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));

			EventFactory factory = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token onto the battlefield that's a copy of Pack Rat.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(factory);
		}
	}

	public PackRat(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Pack Rat's power and toughness are each equal to the number of Rats
		// you control.
		this.addAbility(new PackRatAbility0(state));

		// (2)(B), Discard a card: Put a token onto the battlefield that's a
		// copy of Pack Rat.
		this.addAbility(new PackRatAbility1(state));
	}
}
