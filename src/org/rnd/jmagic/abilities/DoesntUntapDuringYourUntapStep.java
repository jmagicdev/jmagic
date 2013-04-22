/**
 * 
 */
package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class DoesntUntapDuringYourUntapStep extends StaticAbility
{
	private final String creatureName;

	public DoesntUntapDuringYourUntapStep(GameState state, String creatureName)
	{
		super(state, creatureName + " doesn't untap during your untap step.");

		this.creatureName = creatureName;

		EventPattern prohibitPattern = new UntapDuringControllersUntapStep(This.instance());

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
		this.addEffectPart(part);
	}

	@Override
	public DoesntUntapDuringYourUntapStep create(Game game)
	{
		return new DoesntUntapDuringYourUntapStep(game.physicalState, this.creatureName);
	}
}