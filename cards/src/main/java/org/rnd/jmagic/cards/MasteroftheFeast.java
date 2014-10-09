package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Master of the Feast")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class MasteroftheFeast extends Card
{
	public static final class MasteroftheFeastAbility1 extends EventTriggeredAbility
	{
		public MasteroftheFeastAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, each opponent draws a card.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(drawCards(OpponentsOf.instance(You.instance()), 1, "Each opponent draws a card."));
		}
	}

	public MasteroftheFeast(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, each opponent draws a card.
		this.addAbility(new MasteroftheFeastAbility1(state));
	}
}
