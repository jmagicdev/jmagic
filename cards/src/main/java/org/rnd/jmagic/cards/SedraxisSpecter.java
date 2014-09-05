package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sedraxis Specter")
@Types({Type.CREATURE})
@SubTypes({SubType.SPECTER})
@ManaCost("UBR")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class SedraxisSpecter extends Card
{
	public static final class CombatDamageDiscard extends EventTriggeredAbility
	{
		public CombatDamageDiscard(GameState state)
		{
			super(state, "Whenever Sedraxis Specter deals combat damage to a player, that player discards a card.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			this.addEffect(discardCards(TakerOfDamage.instance(TriggerDamage.instance(This.instance())), 1, "That player discards a card."));
		}
	}

	public SedraxisSpecter(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Sedraxis Specter deals combat damage to a player, that
		// player discards a card.
		this.addAbility(new CombatDamageDiscard(state));

		// Unearth (1)(B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(1)(B)"));
	}
}
