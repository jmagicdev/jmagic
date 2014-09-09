package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Niblis of the Mist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class NiblisoftheMist extends Card
{
	public static final class NiblisoftheMistAbility1 extends EventTriggeredAbility
	{
		public NiblisoftheMistAbility1(GameState state)
		{
			super(state, "When Niblis of the Mist enters the battlefield, you may tap target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(tap(target, "Tap target creature.")));
		}
	}

	public NiblisoftheMist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Niblis of the Mist enters the battlefield, you may tap target
		// creature.
		this.addAbility(new NiblisoftheMistAbility1(state));
	}
}
