package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flamespeaker's Will")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class FlamespeakersWill extends Card
{
	public static final class FlamespeakersWillAbility1 extends StaticAbility
	{
		public FlamespeakersWillAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
		}
	}

	public static final class FlamespeakersWillAbility2 extends EventTriggeredAbility
	{
		public FlamespeakersWillAbility2(GameState state)
		{
			super(state, "Whenever enchanted creature deals combat damage to a player, you may sacrifice Flamespeaker's Will. If you do, destroy target artifact.");

			this.addPattern(whenDealsCombatDamageToAPlayer(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

			EventFactory sacrifice = sacrificeThis("Flamespeaker's Will");

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			EventFactory destroy = destroy(target, "Destroy target artifact.");

			this.addEffect(ifThen(youMay(sacrifice), destroy, "You may sacrifice Flamespeaker's Will. If you do, destroy target artifact."));
		}
	}

	public FlamespeakersWill(GameState state)
	{
		super(state);

		// Enchant creature you control
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.CreatureYouControl(state));

		// Enchanted creature gets +1/+1.
		this.addAbility(new FlamespeakersWillAbility1(state));

		// Whenever enchanted creature deals combat damage to a player, you may
		// sacrifice Flamespeaker's Will. If you do, destroy target artifact.
		this.addAbility(new FlamespeakersWillAbility2(state));
	}
}
