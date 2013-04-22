package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vastwood Zendikon")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class VastwoodZendikon extends Card
{
	public VastwoodZendikon(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land is a 6/4 green Elemental creature. It's still a land.
		Animator animator = new Animator(EnchantedBy.instance(This.instance()), 6, 4);
		animator.addColor(Color.GREEN);
		animator.addSubType(SubType.ELEMENTAL);
		this.addAbility(new org.rnd.jmagic.abilities.StaticAnimation(state, animator, "Enchanted land is a 6/4 green Elemental creature. It's still a land."));

		// When enchanted land is put into a graveyard, return that card to its
		// owner's hand.
		this.addAbility(new org.rnd.jmagic.abilities.EnchantedCardComesBackToHand(state, "enchanted land"));
	}
}
