package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kiora, the Crashing Wave")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.KIORA})
@ManaCost("2GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class KioratheCrashingWave extends Card
{
	private static final class Prevent extends DamageReplacementEffect
	{
		private SetGenerator target;

		private Prevent(Game game, String name, SetGenerator target)
		{
			super(game, name);
			this.target = target;
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			Identified ability = this.getSourceObject(context.game.actualState);

			java.util.Set<Integer> IDs = this.target.evaluate(context.state, ability).getAll(Identified.class).stream()//
			.map(i -> i.ID)//
			.collect(java.util.stream.Collectors.toSet());

			return damageAssignments.stream()//
			.filter(dmg -> IDs.contains(dmg.takerID) || IDs.contains(dmg.sourceID))//
			.collect(java.util.stream.Collectors.toCollection(DamageAssignment.Batch::new));
		}

		@Override
		public java.util.List<org.rnd.jmagic.engine.EventFactory> prevent(org.rnd.jmagic.engine.DamageAssignment.Batch damageAssignments)
		{
			damageAssignments.clear();
			return java.util.Collections.emptyList();
		}
	}

	public static final class KioratheCrashingWaveAbility0 extends LoyaltyAbility
	{
		public KioratheCrashingWaveAbility0(GameState state)
		{
			super(state, +1, "Until your next turn, prevent all damage that would be dealt to and dealt by target permanent an opponent controls.");

			EventFactory noteTurn = new EventFactory(NOTE, "");
			noteTurn.parameters.put(EventType.Parameter.OBJECT, CurrentTurn.instance());
			this.addEffect(noteTurn);

			SetGenerator thisTurn = EffectResult.instance(noteTurn);
			SetGenerator yourNextTurn = RelativeComplement.instance(TurnOf.instance(You.instance()), thisTurn);
			SetGenerator untilYourNextTurn = Intersect.instance(yourNextTurn, CurrentTurn.instance());

			SetGenerator enemyStuff = ControlledBy.instance(OpponentsOf.instance(You.instance()));
			SetGenerator target = targetedBy(this.addTarget(enemyStuff, "target permanent an opponent controls"));

			ReplacementEffect replacement = new Prevent(this.game, "Until your next turn, prevent all damage that would be dealt to and dealt by target permanent an opponent controls.", target);
			this.addEffect(createFloatingEffect(untilYourNextTurn, "Until your next turn, prevent all damage that would be dealt to and dealt by target permanent an opponent controls.", replacementEffectPart(replacement)));
		}
	}

	public static final class KioratheCrashingWaveAbility1 extends LoyaltyAbility
	{
		public KioratheCrashingWaveAbility1(GameState state)
		{
			super(state, -1, "Draw a card. You may play an additional land this turn.");
			this.addEffect(drawACard());
			this.addEffect(playExtraLands(You.instance(), 1, "You may play an additional land this turn."));
		}
	}

	public static final class ThatsSomeGoodRum extends EventTriggeredAbility
	{
		public ThatsSomeGoodRum(GameState state)
		{
			super(state, "At the beginning of your end step, put a 9/9 blue Kraken creature token onto the battlefield.");
			this.addPattern(atTheBeginningOfYourEndStep());

			CreateTokensFactory kraken = new CreateTokensFactory(1, 9, 9, "Put a 9/9 blue Kraken creature token onto the battlefield.");
			kraken.setColors(Color.BLUE);
			kraken.setSubTypes(SubType.KRAKEN);
			this.addEffect(kraken.getEventFactory());
		}
	}

	public static final class KioratheCrashingWaveAbility2 extends LoyaltyAbility
	{
		public KioratheCrashingWaveAbility2(GameState state)
		{
			super(state, -5, "You get an emblem with \"At the beginning of your end step, put a 9/9 blue Kraken creature token onto the battlefield.\"");

			EventFactory makeEmblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"At the beginning of your end step, put a 9/9 blue Kraken creature token onto the battlefield.\"");
			makeEmblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeEmblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(ThatsSomeGoodRum.class));
			makeEmblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(makeEmblem);
		}
	}

	public KioratheCrashingWave(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(2);

		// +1: Until your next turn, prevent all damage that would be dealt to
		// and dealt by target permanent an opponent controls.
		this.addAbility(new KioratheCrashingWaveAbility0(state));

		// -1: Draw a card. You may play an additional land this turn.
		this.addAbility(new KioratheCrashingWaveAbility1(state));

		// -5: You get an emblem with
		// "At the beginning of your end step, put a 9/9 blue Kraken creature token onto the battlefield."
		this.addAbility(new KioratheCrashingWaveAbility2(state));
	}
}
