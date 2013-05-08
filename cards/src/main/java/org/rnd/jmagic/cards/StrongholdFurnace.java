package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;

@Name("Stronghold Furnace")
@Types({Type.PLANE})
@SubTypes({SubType.RATH})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class StrongholdFurnace extends Card
{
	public static final class WelcomeToRath extends StaticAbility
	{
		public WelcomeToRath(GameState state)
		{
			super(state, "If a source would deal damage to a creature or player, it deals double that damage to that creature or player instead.");
			this.addEffectPart(replacementEffectPart(new FurnaceofRath.DoubleDamageEffect(this.game)));
			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class ChaosPing extends EventTriggeredAbility
	{
		public ChaosPing(GameState state)
		{
			super(state, "Whenever you roll (C), Stronghold Furnace deals 1 damage to target creature or player.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(permanentDealDamage(1, targetedBy(target), "Stronghold Furnace deals 1 damage to target creature or player."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public StrongholdFurnace(GameState state)
	{
		super(state);

		this.addAbility(new WelcomeToRath(state));

		this.addAbility(new ChaosPing(state));
	}
}
