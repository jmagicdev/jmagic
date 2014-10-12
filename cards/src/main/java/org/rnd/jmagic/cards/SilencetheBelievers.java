package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silence the Believers")
@Types({Type.INSTANT})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class SilencetheBelievers extends Card
{
	public SilencetheBelievers(GameState state)
	{
		super(state);

		// Strive \u2014 Silence the Believers costs (2)(B) more to cast for
		// each target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(2)(B)"));

		// Exile any number of target creatures and all Auras attached to them.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		SetGenerator attached = Intersect.instance(HasSubType.instance(SubType.AURA), AttachedTo.instance(target));
		this.addEffect(exile(Union.instance(target, attached), "Exile any number of target creatures and all Auras attached to them."));
	}
}
