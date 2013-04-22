package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Inside Out")
@Types({Type.INSTANT})
@ManaCost("1(UR)")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class InsideOut extends Card
{
	public InsideOut(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SWITCH_POWER_AND_TOUGHNESS);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));

		this.addEffect(createFloatingEffect("Switch target creature's power and toughness until end of turn.", part));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
