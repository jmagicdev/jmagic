package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Undead Executioner")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class UndeadExecutioner extends Card
{
	public static final class UndeadExecutionerAbility0 extends EventTriggeredAbility
	{
		public UndeadExecutionerAbility0(GameState state)
		{
			super(state, "When Undead Executioner dies, you may have target creature get -2/-2 until end of turn.");
			this.addPattern(whenThisDies());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			EventFactory harm = ptChangeUntilEndOfTurn(target, -2, -2, "Target creature gets -2/-2 until end of turn");
			this.addEffect(youMay(harm, "You may have target creature get -2/-2 until end of turn."));
		}
	}

	public UndeadExecutioner(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Undead Executioner dies, you may have target creature get -2/-2
		// until end of turn.
		this.addAbility(new UndeadExecutionerAbility0(state));
	}
}
