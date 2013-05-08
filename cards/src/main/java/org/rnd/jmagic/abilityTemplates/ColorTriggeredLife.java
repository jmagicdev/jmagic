package org.rnd.jmagic.abilityTemplates;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

abstract public class ColorTriggeredLife extends EventTriggeredAbility
{
	public ColorTriggeredLife(GameState state, Color color)
	{
		super(state, "Whenever a player casts a " + color.name().toLowerCase() + " spell, you may gain 1 life.");

		SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
		pattern.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(color), Spells.instance()));
		this.addPattern(pattern);

		EventFactory gainLife = gainLife(You.instance(), 1, "Gain 1 life");
		this.addEffect(youMay(gainLife, "You may gain 1 life."));
	}
}
