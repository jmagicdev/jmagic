package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sword of Feast and Famine")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class SwordofFeastandFamine extends Card
{
	public static final class SwordofFeastandFamineAbility0 extends StaticAbility
	{
		public static final class ProBG extends org.rnd.jmagic.abilities.keywords.Protection
		{
			public ProBG(GameState state)
			{
				super(state, HasColor.instance(Color.BLACK, Color.GREEN), "black and from green");
			}
		}

		public SwordofFeastandFamineAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2 and has protection from black and from green.");

			SetGenerator equipped = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equipped, +2, +2));

			this.addEffectPart(addAbilityToObject(equipped, ProBG.class));
		}
	}

	public static final class SwordofFeastandFamineAbility1 extends EventTriggeredAbility
	{
		public SwordofFeastandFamineAbility1(GameState state)
		{
			super(state, "Whenever equipped creature deals combat damage to a player, that player discards a card and you untap all lands you control.");
			this.addPattern(whenDealsCombatDamageToAPlayer(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			this.addEffect(discardCards(TakerOfDamage.instance(TriggerDamage.instance(This.instance())), 1, "That player discards a card"));
			this.addEffect(untap(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())), "and untap all lands you control."));
		}
	}

	public SwordofFeastandFamine(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2 and has protection from black and from
		// green.
		this.addAbility(new SwordofFeastandFamineAbility0(state));

		// Whenever equipped creature deals combat damage to a player, that
		// player discards a card and you untap all lands you control.
		this.addAbility(new SwordofFeastandFamineAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
