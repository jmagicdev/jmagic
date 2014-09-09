package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Corrupted Zendikon")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class CorruptedZendikon extends Card
{
	public CorruptedZendikon(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land is a 3/3 black Ooze creature. It's still a land.
		Animator animator = new Animator(EnchantedBy.instance(This.instance()), 3, 3);
		animator.addColor(Color.BLACK);
		animator.addSubType(SubType.OOZE);
		this.addAbility(new org.rnd.jmagic.abilities.StaticAnimation(state, animator, "Enchanted land is a 3/3 black Ooze creature. It's still a land."));

		// When enchanted land is put into a graveyard, return that card to its
		// owner's hand.
		this.addAbility(new org.rnd.jmagic.abilities.EnchantedCardComesBackToHand(state, "enchanted land"));
	}
}
