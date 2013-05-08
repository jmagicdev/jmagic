package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aggravate")
@Types({Type.INSTANT})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Aggravate extends Card
{
	public Aggravate(GameState state)
	{
		super(state);

		// Aggravate deals 1 damage to each creature target player controls.
		// Each creature dealt damage this way attacks this turn if able.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		this.addEffect(spellDealDamage(1, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(target)), "Aggravate deals 1 damage to each creature target player controls."));

		state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());
		SetGenerator damaged = DealtDamageByThisTurn.instance(This.instance());
		SetGenerator damagedCreatures = Intersect.instance(CreaturePermanents.instance(), damaged);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
		part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, damagedCreatures);

		this.addEffect(createFloatingEffect("Each creature dealt damage this way attacks this turn if able.", part));
	}
}
