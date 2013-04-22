package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Coma Veil")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ComaVeil extends Card
{
	public static final class ComaVeilAbility1 extends StaticAbility
	{
		public ComaVeilAbility1(GameState state)
		{
			super(state, "Enchanted permanent doesn't untap during its controller's untap step.");

			EventPattern untapping = new UntapDuringControllersUntapStep(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));
			this.addEffectPart(part);
		}
	}

	public ComaVeil(GameState state)
	{
		super(state);

		// Enchant artifact or creature
		SetGenerator artifactOrCreature = Intersect.instance(CreaturePermanents.instance(), ArtifactPermanents.instance());
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Final(state, "artifact or creature", artifactOrCreature));

		// Enchanted permanent doesn't untap during its controller's untap step.
		this.addAbility(new ComaVeilAbility1(state));
	}
}
