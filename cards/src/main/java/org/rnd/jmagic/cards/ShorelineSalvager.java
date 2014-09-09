package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shoreline Salvager")
@Types({Type.CREATURE})
@SubTypes({SubType.SURRAKAR})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class ShorelineSalvager extends Card
{
	public static final class ShorelineSalvagerAbility0 extends EventTriggeredAbility
	{
		public ShorelineSalvagerAbility0(GameState state)
		{
			super(state, "Whenever Shoreline Salvager deals combat damage to a player, if you control an Island, you may draw a card.");
			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));
			this.interveningIf = Intersect.instance(HasSubType.instance(SubType.ISLAND), ControlledBy.instance(You.instance()));
			this.addEffect(youMay(drawACard(), "You may draw a card."));
		}
	}

	public ShorelineSalvager(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever Shoreline Salvager deals combat damage to a player, if you
		// control an Island, you may draw a card.
		this.addAbility(new ShorelineSalvagerAbility0(state));
	}
}
