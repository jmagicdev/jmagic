package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mana Vault")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class ManaVault extends Card
{
	public static final class ManaVaultAbility1 extends EventTriggeredAbility
	{
		public ManaVaultAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may pay (4). If you do, untap Mana Vault.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory pay4 = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (4)");
			pay4.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay4.parameters.put(EventType.Parameter.PLAYER, You.instance());
			pay4.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("4")));

			EventFactory effect = ifThen(pay4, untap(ABILITY_SOURCE_OF_THIS, "Untap Mana Vault"), "You may pay (4). If you do, untap Mana Vault.");
			this.addEffect(effect);
		}
	}

	public static final class ManaVaultAbility2 extends EventTriggeredAbility
	{
		public ManaVaultAbility2(GameState state)
		{
			super(state, "At the beginning of your draw step, if Mana Vault is tapped, it deals 1 damage to you.");
			this.addPattern(atTheBeginningOfYourDrawStep());

			this.interveningIf = Intersect.instance(Tapped.instance(), ABILITY_SOURCE_OF_THIS);
			this.addEffect(permanentDealDamage(1, You.instance(), "Mana Vault deals 1 damage to you."));
		}
	}

	public ManaVault(GameState state)
	{
		super(state);

		// Mana Vault doesn't untap during your untap step.
		this.addAbility(new org.rnd.jmagic.abilities.DoesntUntapDuringYourUntapStep(state, this.getName()));

		// At the beginning of your upkeep, you may pay (4). If you do, untap
		// Mana Vault.
		this.addAbility(new ManaVaultAbility1(state));

		// At the beginning of your draw step, if Mana Vault is tapped, it deals
		// 1 damage to you.
		this.addAbility(new ManaVaultAbility2(state));

		// (T): Add (3) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(3)"));
	}
}
