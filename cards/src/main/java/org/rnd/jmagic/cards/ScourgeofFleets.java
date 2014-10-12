package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scourge of Fleets")
@Types({Type.CREATURE})
@SubTypes({SubType.KRAKEN})
@ManaCost("5UU")
@ColorIdentity({Color.BLUE})
public final class ScourgeofFleets extends Card
{
	public static final class ScourgeofFleetsAbility0 extends EventTriggeredAbility
	{
		public ScourgeofFleetsAbility0(GameState state)
		{
			super(state, "When Scourge of Fleets enters the battlefield, return each creature your opponents control with toughness X or less to its owner's hand, where X is the number of Islands you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourIslands = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ISLAND));
			SetGenerator X = Count.instance(yourIslands);
			SetGenerator smallStuff = HasToughness.instance(Between.instance(null, X));
			SetGenerator enemySmallStuff = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), smallStuff);
			this.addEffect(bounce(enemySmallStuff, "Return each creature your opponents control with toughness X or less to its owner's hand, where X is the number of Islands you control."));
		}
	}

	public ScourgeofFleets(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// When Scourge of Fleets enters the battlefield, return each creature
		// your opponents control with toughness X or less to its owner's hand,
		// where X is the number of Islands you control.
		this.addAbility(new ScourgeofFleetsAbility0(state));
	}
}
