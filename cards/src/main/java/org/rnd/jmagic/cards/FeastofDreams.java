package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Feast of Dreams")
@Types({Type.INSTANT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class FeastofDreams extends Card
{
	public FeastofDreams(GameState state)
	{
		super(state);

		// Destroy target enchanted creature or enchantment creature.
		SetGenerator enchanted = EnchantedBy.instance(HasSubType.instance(SubType.AURA));
		SetGenerator enchantedCreature = Intersect.instance(enchanted, CreaturePermanents.instance());
		SetGenerator enchantmentCreature = Intersect.instance(HasType.instance(Type.ENCHANTMENT), CreaturePermanents.instance());
		SetGenerator target = targetedBy(this.addTarget(Union.instance(enchantedCreature, enchantmentCreature), "target enchanted creature or enchantment creature"));
		this.addEffect(destroy(target, "Destroy target enchanted creature or enchantment creature."));
	}
}
