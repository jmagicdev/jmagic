package org.rnd.jmagic.abilityTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.NonEmpty;

public abstract class SojournerTrigger extends EventTriggeredAbility
{
	public SojournerTrigger(GameState state, String name)
	{
		super(state, name);
		this.canTrigger = NonEmpty.instance();

		this.addPattern(whenYouCycleThis());
		this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());
	}

}
