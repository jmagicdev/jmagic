package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silvergill Adept")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.WIZARD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SilvergillAdept extends Card
{
	public static final class SilvergillAdeptAbility1 extends EventTriggeredAbility
	{
		public SilvergillAdeptAbility1(GameState state)
		{
			super(state, "When Silvergill Adept enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public SilvergillAdept(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// As an additional cost to cast Silvergill Adept, reveal a Merfolk card
		// from your hand or pay (3).
		SetGenerator fish = Intersect.instance(HasSubType.instance(SubType.MERFOLK), InZone.instance(HandOf.instance(You.instance())));
		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal a Merfolk card from your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, fish);

		EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (3)");
		pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pay.parameters.put(EventType.Parameter.OBJECT, This.instance());
		pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("3")));
		pay.parameters.put(EventType.Parameter.PLAYER, You.instance());

		EventFactory additionalCost = new EventFactory(EventType.CHOOSE_AND_PERFORM, "reveal a Merfolk card from your hand or pay (3)");
		additionalCost.parameters.put(EventType.Parameter.PLAYER, You.instance());
		additionalCost.parameters.put(EventType.Parameter.EVENT, Identity.instance(reveal, pay));
		this.addCost(additionalCost);
		// When Silvergill Adept enters the battlefield, draw a card.
		this.addAbility(new SilvergillAdeptAbility1(state));
	}
}
