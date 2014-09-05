package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sword of Light and Shadow")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Darksteel.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class SwordofLightandShadow extends Card
{
	public static final class SwordofLightandShadowAbility0 extends StaticAbility
	{
		public static final class ProtectionFromWhiteAndFromBlack extends org.rnd.jmagic.abilities.keywords.Protection
		{
			public ProtectionFromWhiteAndFromBlack(GameState state)
			{
				super(state, HasColor.instance(Color.WHITE, Color.BLACK), "white and from black");
			}
		}

		public SwordofLightandShadowAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2 and has protection from white and from black.");

			SetGenerator equipped = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equipped, +2, +2));

			this.addEffectPart(addAbilityToObject(equipped, ProtectionFromWhiteAndFromBlack.class));
		}
	}

	public static final class SwordofLightandShadowAbility1 extends EventTriggeredAbility
	{
		public SwordofLightandShadowAbility1(GameState state)
		{
			super(state, "Whenever equipped creature deals combat damage to a player, you gain 3 life and you may return up to one target creature card from your graveyard to your hand.");

			SetGenerator equipped = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);

			this.addPattern(whenDealsCombatDamageToAPlayer(equipped));

			SetGenerator yourYard = GraveyardOf.instance(You.instance());
			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(yourYard)), "up to one target creature card in your graveyard");
			target.setNumber(0, 1);

			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life"));

			this.addEffect(youMay(putIntoHand(targetedBy(target), You.instance(), "Return up to one target creature card from your graveyard to your hand."), "and you may return up to one target creature card from your graveyard to your hand."));
		}
	}

	public SwordofLightandShadow(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2 and has protection from white and from
		// black.
		this.addAbility(new SwordofLightandShadowAbility0(state));

		// Whenever equipped creature deals combat damage to a player, you gain
		// 3 life and you may return up to one target creature card from your
		// graveyard to your hand.
		this.addAbility(new SwordofLightandShadowAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
