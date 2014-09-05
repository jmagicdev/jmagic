package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Silence")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Silence extends Card
{
	public Silence(GameState state)
	{
		super(state);

		SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
		prohibitPattern.put(EventType.Parameter.OBJECT, SetPattern.CASTABLE);
		prohibitPattern.put(EventType.Parameter.PLAYER, new SimpleSetPattern(OpponentsOf.instance(You.instance())));
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));

		this.addEffect(createFloatingEffect("Your opponents can't cast spells this turn.", part));
	}
}
