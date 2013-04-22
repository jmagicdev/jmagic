package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vizkopa Confessor")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("3WB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class VizkopaConfessor extends Card
{
	public static final EventType PAY_ANY_AMOUNT_OF_LIFE = new EventType("PAY_ANY_AMOUNT_OF_LIFE")
	{
		@Override
		public EventType.Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			int number = player.chooseNumber(new org.rnd.util.NumberRange(0, player.lifeTotal), "Pay any amount of life.");

			Event payLife = payLife(Identity.instance(player), number, "Pay " + number + " life").createEvent(game, event.getSource());
			boolean ret = payLife.perform(event, false);
			event.setResult(payLife.getResult());
			return ret;
		}
	};

	public static final class VizkopaConfessorAbility1 extends EventTriggeredAbility
	{
		public VizkopaConfessorAbility1(GameState state)
		{
			super(state, "When Vizkopa Confessor enters the battlefield, pay any amount of life. Target opponent reveals that many cards from his or her hand. You choose one of them and exile it.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory payLife = new EventFactory(PAY_ANY_AMOUNT_OF_LIFE, "Pay any amount of life.");
			payLife.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(payLife);

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			SetGenerator thatMany = Sum.instance(EffectResult.instance(payLife));

			EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Target opponent reveals that many cards from his or her hand.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, InZone.instance(HandOf.instance(target)));
			reveal.parameters.put(EventType.Parameter.NUMBER, thatMany);
			reveal.parameters.put(EventType.Parameter.PLAYER, target);
			this.addEffect(reveal);

			SetGenerator revealed = EffectResult.instance(reveal);

			EventFactory exile = exile(You.instance(), revealed, 1, "You choose one of them and exile it.");
			this.addEffect(exile);
		}
	}

	public VizkopaConfessor(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));

		// When Vizkopa Confessor enters the battlefield, pay any amount of
		// life. Target opponent reveals that many cards from his or her hand.
		// You choose one of them and exile it.
		this.addAbility(new VizkopaConfessorAbility1(state));
	}
}
