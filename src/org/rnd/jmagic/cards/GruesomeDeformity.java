package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gruesome Deformity")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GruesomeDeformity extends Card
{
	public static final class GruesomeDeformityAbility1 extends StaticAbility
	{
		public GruesomeDeformityAbility1(GameState state)
		{
			super(state, "Enchanted creature has intimidate.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Intimidate.class));
		}
	}

	public GruesomeDeformity(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has intimidate. (It can't be blocked except by
		// artifact creatures and/or creatures that share a color with it.)
		this.addAbility(new GruesomeDeformityAbility1(state));
	}
}
