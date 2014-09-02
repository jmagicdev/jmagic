package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ensoul Artifact")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2015, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class EnsoulArtifact extends Card
{
	public static final class EnsoulArtifactAbility1 extends StaticAbility
	{
		public EnsoulArtifactAbility1(GameState state)
		{
			super(state, "Enchanted artifact is a creature with base power and toughness 5/5 in addition to its other types.");

			Animator ensoul = new Animator(EnchantedBy.instance(This.instance()), 5, 5);
			this.addEffectPart(ensoul.getParts());
		}
	}

	public EnsoulArtifact(GameState state)
	{
		super(state);


		// Enchant artifact
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Artifact(state));

		// Enchanted artifact is a creature with base power and toughness 5/5 in addition to its other types.
		this.addAbility(new EnsoulArtifactAbility1(state));
	}
}
