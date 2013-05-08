package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aeon Chronicler")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class AeonChronicler extends Card
{
	public static final class SetPTtoCardsInHand extends CharacteristicDefiningAbility
	{
		public SetPTtoCardsInHand(GameState state)
		{
			super(state, "Aeon Chronicler's power and toughness are each equal to the number of cards in your hand.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator pt = Count.instance(InZone.instance(HandOf.instance(You.instance())));
			this.addEffectPart(setPowerAndToughness(This.instance(), pt, pt));
		}
	}

	public static final class TickDownDraw extends EventTriggeredAbility
	{
		public TickDownDraw(GameState state)
		{
			super(state, "Whenever a time counter is removed from Aeon Chronicler while it's exiled, draw a card.");
			this.addPattern(whenTimeCounterIsRemovedFromThis());
			this.canTrigger = Intersect.instance(ABILITY_SOURCE_OF_THIS, InZone.instance(ExileZone.instance()));
			this.addEffect(drawACard());
		}
	}

	public AeonChronicler(GameState state)
	{
		super(state);

		// Aeon Chronicler's power and toughness are each equal to the number of
		// cards in your hand.
		this.addAbility(new SetPTtoCardsInHand(state));

		// Suspend X\u2014(X)(3)(U). X can't be 0.
		this.addAbility(org.rnd.jmagic.abilities.keywords.Suspend.X(state, "(X)(3)(U)"));

		// Whenever a time counter is removed from Aeon Chronicler while it's
		// exiled, draw a card.
		this.addAbility(new TickDownDraw(state));
	}
}
