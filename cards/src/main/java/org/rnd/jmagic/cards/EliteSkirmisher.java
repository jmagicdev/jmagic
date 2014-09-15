package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elite Skirmisher")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class EliteSkirmisher extends Card
{
	public static final class EliteSkirmisherAbility0 extends EventTriggeredAbility
	{
		public EliteSkirmisherAbility0(GameState state)
		{
			super(state, "Whenever you cast a spell that targets Elite Skirmisher, you may tap target creature.");
			this.addPattern(heroic());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(tap(target, "Tap target creature")));
		}
	}

	public EliteSkirmisher(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Heroic \u2014 Whenever you cast a spell that targets Elite
		// Skirmisher, you may tap target creature.
		this.addAbility(new EliteSkirmisherAbility0(state));
	}
}
