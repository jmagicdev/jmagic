package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Slavering Nulls")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.ZOMBIE})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class SlaveringNulls extends Card
{
	public static final class CombatDamageDiscard extends EventTriggeredAbility
	{
		public CombatDamageDiscard(GameState state)
		{
			super(state, "Whenever Slavering Nulls deals combat damage to a player, if you control a Swamp, you may have that player discard a card.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			this.interveningIf = Intersect.instance(HasSubType.instance(SubType.SWAMP), ControlledBy.instance(You.instance()));

			EventFactory discard = discardCards(TakerOfDamage.instance(TriggerDamage.instance(This.instance())), 1, "That player discards a card");
			this.addEffect(youMay(discard, "You may have that player discard a card."));
		}
	}

	public SlaveringNulls(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever Slavering Nulls deals combat damage to a player, if you
		// control a Swamp, you may have that player discard a card.
		this.addAbility(new CombatDamageDiscard(state));
	}
}
