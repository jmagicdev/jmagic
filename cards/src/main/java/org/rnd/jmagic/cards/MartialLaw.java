package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Martial Law")
@Types({Type.ENCHANTMENT})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class MartialLaw extends Card
{
	public static final class MartialLawAbility0 extends EventTriggeredAbility
	{
		public MartialLawAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, detain target creature an opponent controls.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls"));

			this.addEffect(detain(target, "Detain target creature an opponent controls."));

			state.ensureTracker(new DetainGenerator.Tracker());
		}
	}

	public MartialLaw(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, detain target creature an opponent
		// controls. (Until your next turn, that creature can't attack or block
		// and its activated abilities can't be activated.)
		this.addAbility(new MartialLawAbility0(state));
	}
}
