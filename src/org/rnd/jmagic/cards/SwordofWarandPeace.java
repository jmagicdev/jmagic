package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sword of War and Peace")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class SwordofWarandPeace extends Card
{
	public static final class SwordofWarandPeaceAbility0 extends StaticAbility
	{
		public static final class ProRW extends org.rnd.jmagic.abilities.keywords.Protection
		{
			public ProRW(GameState state)
			{
				super(state, HasColor.instance(Color.RED, Color.WHITE), "red and from white");
			}
		}

		public SwordofWarandPeaceAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2 and has protection from red and from white.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equipped, +2, +2));
			this.addEffectPart(addAbilityToObject(equipped, ProRW.class));
		}
	}

	public static final class SwordofWarandPeaceAbility1 extends EventTriggeredAbility
	{
		public SwordofWarandPeaceAbility1(GameState state)
		{
			super(state, "Whenever equipped creature deals combat damage to a player, Sword of War and Peace deals damage to that player equal to the number of cards in his or her hand and you gain 1 life for each card in your hand.");
			this.addPattern(whenDealsCombatDamageToAPlayer(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			SetGenerator damage = Count.instance(InZone.instance(HandOf.instance(thatPlayer)));
			this.addEffect(permanentDealDamage(damage, thatPlayer, "Sword of War and Peace deals damage to that player equal to the number of cards in his or her hand"));

			SetGenerator life = Count.instance(InZone.instance(HandOf.instance(You.instance())));
			this.addEffect(gainLife(You.instance(), life, "and you gain 1 life for each card in your hand."));
		}
	}

	public SwordofWarandPeace(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2 and has protection from red and from
		// white.
		this.addAbility(new SwordofWarandPeaceAbility0(state));

		// Whenever equipped creature deals combat damage to a player, Sword of
		// War and Peace deals damage to that player equal to the number of
		// cards in his or her hand and you gain 1 life for each card in your
		// hand.
		this.addAbility(new SwordofWarandPeaceAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
