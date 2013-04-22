package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demonic Dread")
@Types({Type.SORCERY})
@ManaCost("1BR")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class DemonicDread extends Card
{
	public DemonicDread(GameState state)
	{
		super(state);

		// Cascade
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cascade(state));

		// Target creature can't block this turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
		part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetedBy(target))));
		this.addEffect(createFloatingEffect("Target creature can't block this turn.", part));
	}
}
