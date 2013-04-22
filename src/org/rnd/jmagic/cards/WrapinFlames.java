package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wrap in Flames")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class WrapinFlames extends Card
{
	public WrapinFlames(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "up to three target creatures");
		target.setNumber(0, 3);
		SetGenerator targetGenerator = targetedBy(target);

		// Wrap in Flames deals 1 damage to each of up to three target
		// creatures. Those creatures can't block this turn.
		this.addEffect(spellDealDamage(1, targetGenerator, "Wrap in Flames deals 1 damage to each of up to three target creatures."));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
		part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetGenerator)));
		this.addEffect(createFloatingEffect("Those creatures can't block this turn.", part));
	}
}
