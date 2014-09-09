package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Festering Goblin")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.GOBLIN})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class FesteringGoblin extends Card
{
	public static final class Fester extends EventTriggeredAbility
	{
		public Fester(GameState state)
		{
			super(state, "When Festering Goblin dies, target creature gets -1/-1 until end of turn.");

			this.addPattern(whenThisDies());
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (-1), (-1), "Target creature gets -1/-1 until end of turn."));
		}
	}

	public FesteringGoblin(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Fester(state));
	}
}
