package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Murder of Crows")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class MurderofCrows extends Card
{
	public static final class MurderofCrowsAbility1 extends EventTriggeredAbility
	{
		public MurderofCrowsAbility1(GameState state)
		{
			super(state, "Whenever another creature dies, you may draw a card. If you do, discard a card.");
			this.addPattern(whenAnotherCreatureIsPutIntoAGraveyardFromTheBattlefield());
			this.addEffect(ifThen(youMay(drawACard()), discardCards(You.instance(), 1, "Discard a card."), "You may draw a card. If you do, discard a card."));
		}
	}

	public MurderofCrows(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever another creature dies, you may draw a card. If you do,
		// discard a card.
		this.addAbility(new MurderofCrowsAbility1(state));
	}
}
