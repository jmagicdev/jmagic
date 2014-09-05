package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Stun")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.COMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Stun extends Card
{
	public Stun(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
		part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetedBy(target))));

		this.addEffect(createFloatingEffect("Target creature can't block this turn.", part));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
