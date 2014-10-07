package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Doomwake Giant")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class DoomwakeGiant extends Card
{
	public static final class DoomwakeGiantAbility0 extends EventTriggeredAbility
	{
		public DoomwakeGiantAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Doomwake Giant or another enchantment enters the battlefield under your control, creatures your opponents control get -1/-1 until end of turn.");
			this.addPattern(constellation());
			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));
			this.addEffect(ptChangeUntilEndOfTurn(enemyCreatures, -1, -1, "Creatures your opponents control get -1/-1 until end of turn."));
		}
	}

	public DoomwakeGiant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		// Constellation \u2014 Whenever Doomwake Giant or another enchantment
		// enters the battlefield under your control, creatures your opponents
		// control get -1/-1 until end of turn.
		this.addAbility(new DoomwakeGiantAbility0(state));
	}
}
