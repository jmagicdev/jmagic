package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sword of Body and Mind")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@ColorIdentity({})
public final class SwordofBodyandMind extends Card
{
	public static final class SwordofBodyandMindAbility0 extends StaticAbility
	{
		public static final class ProGU extends org.rnd.jmagic.abilities.keywords.Protection
		{
			public ProGU(GameState state)
			{
				super(state, HasColor.instance(Color.GREEN, Color.BLUE), "green and from blue");
			}
		}

		public SwordofBodyandMindAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2 and has protection from green and from blue.");

			SetGenerator equipped = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equipped, +2, +2));

			this.addEffectPart(addAbilityToObject(equipped, ProGU.class));
		}
	}

	public static final class SwordofBodyandMindAbility1 extends EventTriggeredAbility
	{
		public SwordofBodyandMindAbility1(GameState state)
		{
			super(state, "Whenever equipped creature deals combat damage to a player, you put a 2/2 green Wolf creature token onto the battlefield and that player puts the top ten cards of his or her library into his or her graveyard.");
			this.addPattern(whenDealsCombatDamageToAPlayer(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 green Wolf creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.WOLF);
			this.addEffect(token.getEventFactory());

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(millCards(thatPlayer, 10, "That player puts the top ten cards of his or her library into his or her graveyard."));
		}
	}

	public SwordofBodyandMind(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2 and has protection from green and from
		// blue.
		this.addAbility(new SwordofBodyandMindAbility0(state));

		// Whenever equipped creature deals combat damage to a player, you put a
		// 2/2 green Wolf creature token onto the battlefield and that player
		// puts the top ten cards of his or her library into his or her
		// graveyard.
		this.addAbility(new SwordofBodyandMindAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
