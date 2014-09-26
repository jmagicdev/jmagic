package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Morkrut Banshee")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class MorkrutBanshee extends Card
{
	public static final class MorkrutBansheeAbility0 extends EventTriggeredAbility
	{
		public MorkrutBansheeAbility0(GameState state)
		{
			super(state, "Morbid \u2014 When Morkrut Banshee enters the battlefield, if a creature died this turn, target creature gets -4/-4 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = Morbid.instance();

			state.ensureTracker(new Morbid.Tracker());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, -4, -4, "Target creature gets -4/-4 until end of turn."));
		}
	}

	public MorkrutBanshee(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Morbid \u2014 When Morkrut Banshee enters the battlefield, if a
		// creature died this turn, target creature gets -4/-4 until end of
		// turn.
		this.addAbility(new MorkrutBansheeAbility0(state));
	}
}
