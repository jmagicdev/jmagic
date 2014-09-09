package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Viridian Harvest")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class ViridianHarvest extends Card
{
	public static final class ViridianHarvestAbility1 extends EventTriggeredAbility
	{
		public ViridianHarvestAbility1(GameState state)
		{
			super(state, "When enchanted artifact is put into a graveyard, you gain 6 life.");
			this.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), true));
			this.addEffect(gainLife(You.instance(), 6, "You gain 6 life."));
		}
	}

	public ViridianHarvest(GameState state)
	{
		super(state);

		// Enchant artifact
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Artifact(state));

		// When enchanted artifact is put into a graveyard, you gain 6 life.
		this.addAbility(new ViridianHarvestAbility1(state));
	}
}
