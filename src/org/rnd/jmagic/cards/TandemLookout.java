package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Tandem Lookout")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.HUMAN})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class TandemLookout extends Card
{
	public static final class DealDamageToOpponentDrawACard extends EventTriggeredAbility
	{
		public DealDamageToOpponentDrawACard(GameState state)
		{
			super(state, "Whenever this creature deals damage to an opponent, draw a card.");
			this.addPattern(whenDealsDamageToAPlayer(ABILITY_SOURCE_OF_THIS));
			this.addEffect(drawACard());
		}
	}

	public TandemLookout(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Tandem Lookout is paired with another creature, each of
		// those creatures has
		// "Whenever this creature deals damage to an opponent, draw a card."
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Tandem Lookout is paired with another creature, each of those creatures has \"Whenever this creature deals damage to an opponent, draw a card.\"", DealDamageToOpponentDrawACard.class));
	}
}
