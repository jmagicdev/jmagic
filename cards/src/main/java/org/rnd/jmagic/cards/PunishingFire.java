package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Punishing Fire")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class PunishingFire extends Card
{
	public static final class Punish extends EventTriggeredAbility
	{
		public Punish(GameState state)
		{
			super(state, "Whenever an opponent gains life, you may pay (R). If you do, return Punishing Fire from your graveyard to your hand.");
			this.triggersFromGraveyard();

			SimpleEventPattern triggerPattern = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);
			triggerPattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			this.addPattern(triggerPattern);

			EventFactory youMayPayR = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (R)");
			youMayPayR.parameters.put(EventType.Parameter.CAUSE, This.instance());
			youMayPayR.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("R")));
			youMayPayR.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory moveToHand = new EventFactory(EventType.MOVE_OBJECTS, "Return Punishing Fire from your graveyard to your hand.");
			moveToHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moveToHand.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			moveToHand.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);

			EventFactory totalEffect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (R). If you do, return Punishing Fire from your graveyard to your hand.");
			totalEffect.parameters.put(EventType.Parameter.IF, Identity.instance(youMayPayR));
			totalEffect.parameters.put(EventType.Parameter.THEN, Identity.instance(moveToHand));
			this.addEffect(totalEffect);
		}
	}

	public PunishingFire(GameState state)
	{
		super(state);

		// Punishing Fire deals 2 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(2, targetedBy(target), "Punishing Fire deals 2 damage to target creature or player."));

		// Whenever an opponent gains life, you may pay (R). If you do, return
		// Punishing Fire from your graveyard to your hand.
		this.addAbility(new Punish(state));
	}
}
