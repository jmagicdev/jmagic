package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("One Thousand Lashes")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2WB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class OneThousandLashes extends Card
{
	public static final class OneThousandLashesAbility1 extends StaticAbility
	{
		public OneThousandLashesAbility1(GameState state)
		{
			super(state, "Enchanted creature can't attack or block, and its activated abilities can't be activated.");

			// Enchanted creature
			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			// can't attack
			ContinuousEffect.Part restrictions = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			restrictions.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(enchantedCreature, Attacking.instance())));
			this.addEffectPart(restrictions);

			// or block,
			ContinuousEffect.Part block = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			block.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(enchantedCreature, Blocking.instance())));
			this.addEffectPart(block);

			// and its activated abilities can't be activated.
			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(enchantedCreature));

			ContinuousEffect.Part prohibition = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibition.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(prohibition);

		}
	}

	public static final class OneThousandLashesAbility2 extends EventTriggeredAbility
	{
		public OneThousandLashesAbility2(GameState state)
		{
			super(state, "At the beginning of the upkeep of enchanted creature's controller, that player loses 1 life.");

			SetGenerator enchantedCreaturesController = ControllerOf.instance(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(enchantedCreaturesController));
			this.addPattern(pattern);

			this.addEffect(loseLife(OwnerOf.instance(CurrentStep.instance()), 1, "That player loses 1 life."));
		}
	}

	public OneThousandLashes(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature can't attack or block, and its activated abilities
		// can't be activated.
		this.addAbility(new OneThousandLashesAbility1(state));

		// At the beginning of the upkeep of enchanted creature's controller,
		// that player loses 1 life.
		this.addAbility(new OneThousandLashesAbility2(state));
	}
}
