package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burn the Impure")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BurntheImpure extends Card
{
	public BurntheImpure(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		// Burn the Impure deals 3 damage to target creature.
		this.addEffect(spellDealDamage(3, target, "Burn the Impure deals 3 damage to target creature."));

		// If that creature has infect, Burn the Impure deals 3 damage to that
		// creature's controller.
		EventFactory factory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If that creature has infect, Burn the Impure deals 3 damage to that creature's controller.");
		factory.parameters.put(EventType.Parameter.IF, Intersect.instance(target, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Infect.class)));
		factory.parameters.put(EventType.Parameter.THEN, Identity.instance(spellDealDamage(3, ControllerOf.instance(target), "Burn the Impure deals 3 damage to that creature's controller.")));
		this.addEffect(factory);
	}
}
