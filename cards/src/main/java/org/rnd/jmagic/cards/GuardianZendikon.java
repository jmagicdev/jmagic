package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Guardian Zendikon")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class GuardianZendikon extends Card
{
	public GuardianZendikon(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land is a 2/6 white Wall creature with defender. It's still
		// a land.
		Animator animator = new Animator(EnchantedBy.instance(This.instance()), 2, 6);
		animator.addColor(Color.WHITE);
		animator.addSubType(SubType.WALL);
		animator.addAbility(org.rnd.jmagic.abilities.keywords.Defender.class);
		this.addAbility(new org.rnd.jmagic.abilities.StaticAnimation(state, animator, "Enchanted land is a 2/6 white Wall creature with defender. It's still a land."));

		// When enchanted land is put into a graveyard, return that card to its
		// owner's hand.
		this.addAbility(new org.rnd.jmagic.abilities.EnchantedCardComesBackToHand(state, "enchanted land"));
	}
}
