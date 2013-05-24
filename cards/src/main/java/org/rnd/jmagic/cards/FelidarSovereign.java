package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Felidar Sovereign")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST, SubType.CAT})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class FelidarSovereign extends Card
{
	public static final class ConwaysGameOfLife extends EventTriggeredAbility
	{
		public ConwaysGameOfLife(GameState state)
		{
			super(state, "At the beginning of your upkeep, if you have 40 or more life, you win the game.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			this.interveningIf = Intersect.instance(Between.instance(40, null), LifeTotalOf.instance(You.instance()));

			this.addEffect(youWinTheGame());
		}
	}

	public FelidarSovereign(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		// Vigilance, lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// At the beginning of your upkeep, if you have 40 or more life, you win
		// the game.
		this.addAbility(new ConwaysGameOfLife(state));
	}
}
