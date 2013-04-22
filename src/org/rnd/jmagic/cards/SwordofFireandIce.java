package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sword of Fire and Ice")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.RARE)})
@ColorIdentity({})
public final class SwordofFireandIce extends Card
{
	public static final class SwordofFireandIceAbility0 extends StaticAbility
	{
		public static final class ProtectionFromRedAndFromBlue extends org.rnd.jmagic.abilities.keywords.Protection
		{
			public ProtectionFromRedAndFromBlue(GameState state)
			{
				super(state, HasColor.instance(Color.RED, Color.BLUE), "red and from blue");
			}
		}

		public SwordofFireandIceAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2 and has protection from red and from blue.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equippedCreature, +2, +2));
			this.addEffectPart(addAbilityToObject(equippedCreature, ProtectionFromRedAndFromBlue.class));
		}
	}

	public static final class SwordofFireandIceAbility1 extends EventTriggeredAbility
	{
		public SwordofFireandIceAbility1(GameState state)
		{
			super(state, "Whenever equipped creature deals combat damage to a player, Sword of Fire and Ice deals 2 damage to target creature or player and you draw a card.");
			this.addPattern(whenDealsCombatDamageToAPlayer(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Sword of Fire and Ice deals 2 damage to target creature or player"));

			this.addEffect(drawCards(You.instance(), 1, "and you draw a card."));
		}
	}

	public SwordofFireandIce(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2 and has protection from red and from
		// blue.
		this.addAbility(new SwordofFireandIceAbility0(state));

		// Whenever equipped creature deals combat damage to a player, Sword of
		// Fire and Ice deals 2 damage to target creature or player and you draw
		// a card.
		this.addAbility(new SwordofFireandIceAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
