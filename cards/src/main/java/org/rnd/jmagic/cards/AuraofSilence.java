package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Aura of Silence")
@Types({Type.ENCHANTMENT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Weatherlight.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AuraofSilence extends Card
{
	// Artifact and enchantment spells your opponents cast cost 2 more to cast.
	public static final class Silence extends StaticAbility
	{
		public Silence(GameState state)
		{
			super(state, "Artifact and enchantment spells your opponents cast cost (2) more to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			SetGenerator artifactsAndEnchantments = HasType.instance(Type.ARTIFACT, Type.ENCHANTMENT);
			SetGenerator yourOpponents = OpponentsOf.instance(ControllerOf.instance(This.instance()));
			SetGenerator spellsYourOpponentsCast = Intersect.instance(Spells.instance(), ControlledBy.instance(yourOpponents, Stack.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(spellsYourOpponentsCast, artifactsAndEnchantments));
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("2")));
			this.addEffectPart(part);
		}

	}

	public static final class Disenchant extends ActivatedAbility
	{
		// Sacrifice Aura of Silence: Destroy target artifact or enchantment.
		public Disenchant(GameState state)
		{
			super(state, "Sacrifice Aura of Silence: Destroy target artifact or enchantment.");

			this.addCost(sacrificeThis("Aura of Silence"));

			Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
			this.addEffect(destroy(targetedBy(target), "Destroy target artifact or enchantment."));
		}
	}

	public AuraofSilence(GameState state)
	{
		super(state);

		this.addAbility(new Silence(state));
		this.addAbility(new Disenchant(state));
	}
}
