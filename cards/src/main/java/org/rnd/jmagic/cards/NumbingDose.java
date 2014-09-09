package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Numbing Dose")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class NumbingDose extends Card
{
	public static final class NumbingDoseAbility1 extends StaticAbility
	{
		public NumbingDoseAbility1(GameState state)
		{
			super(state, "Enchanted permanent doesn't untap during its controller's untap step.");

			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(EnchantedBy.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public static final class NumbingDoseAbility2 extends EventTriggeredAbility
	{
		public NumbingDoseAbility2(GameState state)
		{
			super(state, "At the beginning of the upkeep of enchanted permanent's controller, that player loses 1 life.");

			SetGenerator thatPlayer = ControllerOf.instance(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(thatPlayer));
			this.addPattern(pattern);

			this.addEffect(loseLife(thatPlayer, 1, "That player loses 1 life."));
		}
	}

	public NumbingDose(GameState state)
	{
		super(state);

		// Enchant artifact or creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Final(state, "artifact or creature", Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance())));

		// Enchanted permanent doesn't untap during its controller's untap step.
		this.addAbility(new NumbingDoseAbility1(state));

		// At the beginning of the upkeep of enchanted permanent's controller,
		// that player loses 1 life.
		this.addAbility(new NumbingDoseAbility2(state));
	}
}
