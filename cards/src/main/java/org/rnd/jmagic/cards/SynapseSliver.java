package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Synapse Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Legions.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SynapseSliver extends Card
{
	public static final class ShadowmageSliver extends EventTriggeredAbility
	{
		public ShadowmageSliver(GameState state)
		{
			super(state, "Whenever a Sliver deals combat damage to a player, its controller may draw a card.");

			this.addPattern(whenDealsCombatDamageToAPlayer(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.SLIVER))));

			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			SetGenerator itsController = ControllerOf.instance(SourceOfDamage.instance(triggerDamage));

			this.addEffect(playerMay(itsController, drawCards(itsController, 1, "Its controller draws a card."), "Its controller may draw a card."));
		}
	}

	public SynapseSliver(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever a Sliver deals combat damage to a player, its controller may
		// draw a card.
		this.addAbility(new ShadowmageSliver(state));
	}
}
