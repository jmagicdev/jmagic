package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Myr Battlesphere")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR, SubType.CONSTRUCT})
@ManaCost("7")
@ColorIdentity({})
public final class MyrBattlesphere extends Card
{
	public static final class MyrBattlesphereAbility0 extends EventTriggeredAbility
	{
		public MyrBattlesphereAbility0(GameState state)
		{
			super(state, "When Myr Battlesphere enters the battlefield, put four 1/1 colorless Myr artifact creature tokens onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			CreateTokensFactory factory = new CreateTokensFactory(4, 1, 1, "Put four 1/1 colorless Myr artifact creature tokens onto the battlefield.");
			factory.setSubTypes(SubType.MYR);
			factory.setArtifact();
			this.addEffect(factory.getEventFactory());
		}
	}

	public static final class MyrBattlesphereAbility1 extends EventTriggeredAbility
	{
		public MyrBattlesphereAbility1(GameState state)
		{
			super(state, "Whenever Myr Battlesphere attacks, you may tap X untapped Myr you control. If you do, Myr Battlesphere gets +X/+0 until end of turn and deals X damage to defending player.");
			this.addPattern(whenThisAttacks());

			EventFactory tap = new EventFactory(EventType.TAP_CHOICE, "You may tap X untapped Myr you control.");
			tap.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tap.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tap.parameters.put(EventType.Parameter.CHOICE, This.instance());
			tap.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, null));

			SetGenerator X = Count.instance(EffectResult.instance(tap));

			EventFactory pump = createFloatingEffect("Myr Battlesphere gets +X/+0 until end of turn", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, X, numberGenerator(0)));
			EventFactory damage = permanentDealDamage(X, DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS), "and deals X damage to defending player.");

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may tap X untapped Myr you control. If you do, Myr Battlesphere gets +X/+0 until end of turn and deals X damage to defending player.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(tap));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(pump, damage)));
			this.addEffect(factory);
		}
	}

	public MyrBattlesphere(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(7);

		// When Myr Battlesphere enters the battlefield, put four 1/1 colorless
		// Myr artifact creature tokens onto the battlefield.
		this.addAbility(new MyrBattlesphereAbility0(state));

		// Whenever Myr Battlesphere attacks, you may tap X untapped Myr you
		// control. If you do, Myr Battlesphere gets +X/+0 until end of turn and
		// deals X damage to defending player.
		this.addAbility(new MyrBattlesphereAbility1(state));
	}
}
