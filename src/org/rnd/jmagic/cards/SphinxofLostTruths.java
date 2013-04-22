package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sphinx of Lost Truths")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SphinxofLostTruths extends Card
{
	public static final class DrawDiscard extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public DrawDiscard(GameState state, CostCollection kickerCost)
		{
			super(state, "When Sphinx of Lost Truths enters the battlefield, draw three cards. Then if it wasn't kicked, discard three cards.");

			this.kickerCost = kickerCost;

			this.addPattern(whenThisEntersTheBattlefield());

			this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));

			EventFactory discard = new EventFactory(EventType.DISCARD_CHOICE, "Discard three cards");
			discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			discard.parameters.put(EventType.Parameter.PLAYER, You.instance());
			discard.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));

			EventFactory conditionalDiscard = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Then if it wasn't kicked, discard three cards.");
			conditionalDiscard.parameters.put(EventType.Parameter.IF, Not.instance(ThisPermanentWasKicked.instance(kickerCost)));
			conditionalDiscard.parameters.put(EventType.Parameter.THEN, Identity.instance(discard));
			this.addEffect(conditionalDiscard);
		}

		@Override
		public DrawDiscard create(Game game)
		{
			return new DrawDiscard(game.physicalState, this.kickerCost);
		}
	}

	public SphinxofLostTruths(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "1U");
		this.addAbility(ability);

		// Kicker (1)(U) (You may pay an additional (1)(U) as you cast this
		// spell.)
		CostCollection kickerCost = ability.costCollections[0];

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Sphinx of Lost Truths enters the battlefield, draw three cards.
		// Then if it wasn't kicked, discard three cards.
		this.addAbility(new DrawDiscard(state, kickerCost));
	}
}
