package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Greenside Watcher")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.DRUID})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class GreensideWatcher extends Card
{
	public static final class GreensideWatcherAbility0 extends ActivatedAbility
	{
		public GreensideWatcherAbility0(GameState state)
		{
			super(state, "(T): Untap target Gate.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.GATE)), "target Gate"));
			this.addEffect(untap(target, "Untap target Gate."));
		}
	}

	public GreensideWatcher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (T): Untap target Gate.
		this.addAbility(new GreensideWatcherAbility0(state));
	}
}
