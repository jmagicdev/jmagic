package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Choke")
@Types({Type.ENCHANTMENT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class Choke extends Card
{
	public static final class ChokeAbility0 extends StaticAbility
	{
		public ChokeAbility0(GameState state)
		{
			super(state, "Islands don't untap during their controllers' untap steps.");

			SetGenerator islands = Intersect.instance(HasSubType.instance(SubType.ISLAND), Permanents.instance());
			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(islands);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public Choke(GameState state)
	{
		super(state);

		// Islands don't untap during their controllers' untap steps.
		this.addAbility(new ChokeAbility0(state));
	}
}
