package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Corpse Lunge")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class CorpseLunge extends Card
{
	public CorpseLunge(GameState state)
	{
		super(state);

		// As an additional cost to cast Corpse Lunge, exile a creature card
		// from your graveyard.
		EventFactory exile = exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 1, "exile a creature card from your graveyard");
		this.addCost(exile);

		// Corpse Lunge deals damage equal to the exiled card's power to target
		// creature.
		SetGenerator amount = PowerOf.instance(NewObjectOf.instance(EffectResult.instance(exile)));
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(amount, target, "Corpse Lunge deals damage equal to the exiled card's power to target creature."));
	}
}
