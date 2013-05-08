package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class WardenTrigger extends EventTriggeredAbility
{
	public WardenTrigger(GameState state)
	{
		super(state, "Whenever another creature enters the battlefield, you gain 1 life.");

		this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS), false));

		this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
	}
}
