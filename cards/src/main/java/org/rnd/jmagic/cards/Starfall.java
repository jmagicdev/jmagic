package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Starfall")
@Types({Type.INSTANT})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class Starfall extends Card
{
	public Starfall(GameState state)
	{
		super(state);

		// Starfall deals 3 damage to target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(3, target, "Starfall deals 3 damage to target creature."));

		// If that creature is an enchantment, Starfall deals 3 damage to that
		// creature's controller.
		SetGenerator isEnchantment = Intersect.instance(target, EnchantmentPermanents.instance());
		SetGenerator controller = ControllerOf.instance(target);
		EventFactory damage = spellDealDamage(3, controller, "Starfall deals 3 damage to that creature's controller");
		this.addEffect(ifThen(isEnchantment, damage, "If that creature is an enchantment, Starfall deals 3 damage to that creature's controller."));
	}
}
