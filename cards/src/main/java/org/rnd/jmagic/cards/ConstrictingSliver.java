package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Constricting Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("5W")
@ColorIdentity({Color.WHITE})
public final class ConstrictingSliver extends Card
{
	public static class ETBExile extends EventTriggeredAbility
	{
		public ETBExile(GameState state)
		{
			super(state, "When this creature enters the battlefield, exile target creature an opponent controls until this creature leaves the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator opponentsCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			Target target = this.addTarget(opponentsCreatures, "target creature an opponent controls");

			state.ensureTracker(new LeftTheBattlefield.LeavesTheBattlefieldTracker());
			SetGenerator thisIsGone = Intersect.instance(ABILITY_SOURCE_OF_THIS, LeftTheBattlefield.instance());

			EventFactory exileUntil = new EventFactory(EventType.EXILE_UNTIL, "Exile target creature an opponent controls until this creature leaves the battlefield.");
			exileUntil.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileUntil.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			exileUntil.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(thisIsGone));
			this.addEffect(exileUntil);
		}
	}

	public static final class ConstrictingSliverAbility0 extends StaticAbility
	{
		public ConstrictingSliverAbility0(GameState state)
		{
			super(state, "Sliver creatures you control have \"When this creature enters the battlefield, you may exile target creature an opponent controls until this creature leaves the battlefield.\"");
			this.addEffectPart(addAbilityToObject(SLIVER_CREATURES_YOU_CONTROL, ETBExile.class));
		}
	}

	public ConstrictingSliver(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Sliver creatures you control have
		// "When this creature enters the battlefield, you may exile target creature an opponent controls until this creature leaves the battlefield."
		this.addAbility(new ConstrictingSliverAbility0(state));
	}
}
