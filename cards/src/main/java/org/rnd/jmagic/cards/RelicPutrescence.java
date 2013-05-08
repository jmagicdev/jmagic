package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Relic Putrescence")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class RelicPutrescence extends Card
{
	public static final class RelicPutrescenceAbility1 extends EventTriggeredAbility
	{
		public RelicPutrescenceAbility1(GameState state)
		{
			super(state, "Whenever enchanted artifact becomes tapped, its controller gets a poison counter.");

			SetGenerator enchantedArtifact = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			pattern.put(EventType.Parameter.OBJECT, enchantedArtifact);
			this.addPattern(pattern);

			this.addEffect(putCounters(1, Counter.CounterType.POISON, ControllerOf.instance(enchantedArtifact), "Its controller gets a poison counter."));
		}
	}

	public RelicPutrescence(GameState state)
	{
		super(state);

		// Enchant artifact
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Final(state, "artifact", ArtifactPermanents.instance()));

		// Whenever enchanted artifact becomes tapped, its controller gets a
		// poison counter.
		this.addAbility(new RelicPutrescenceAbility1(state));
	}
}
