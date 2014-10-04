package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ajani, Mentor of Heroes")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.AJANI})
@ManaCost("3GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class AjaniMentorofHeroes extends Card
{
	public static final class AjaniMentorofHeroesAbility0 extends LoyaltyAbility
	{
		public AjaniMentorofHeroesAbility0(GameState state)
		{
			super(state, +1, "Distribute three +1/+1 counters among one, two, or three target creatures you control.");

			SetGenerator target = targetedDistribute(this.addTarget(CREATURES_YOU_CONTROL, "one, two, or three target creatures you control").setNumber(1, 3));
			this.setDivision(Identity.instance(3, "+1/+1 counters"));

			EventFactory effect = new EventFactory(EventType.DISTRIBUTE_COUNTERS, "Distribute three +1/+1 counters among one, two, or three target creatures you control.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, target);
			effect.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));

		}
	}

	public static final class AjaniMentorofHeroesAbility1 extends LoyaltyAbility
	{
		public AjaniMentorofHeroesAbility1(GameState state)
		{
			super(state, +1, "Look at the top four cards of your library. You may reveal an Aura, creature, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order.");

			EventFactory effect = new EventFactory(LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM, "Look at the top four cards of your library. You may reveal an Aura, creature, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(4));
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.ZONE, LibraryOf.instance(You.instance()));
			effect.parameters.put(EventType.Parameter.TYPE, Union.instance(HasSubType.instance(SubType.AURA), HasType.instance(Type.CREATURE, Type.PLANESWALKER)));
			this.addEffect(effect);
		}
	}

	public static final class AjaniMentorofHeroesAbility2 extends LoyaltyAbility
	{
		public AjaniMentorofHeroesAbility2(GameState state)
		{
			super(state, -8, "You gain 100 life.");
			this.addEffect(gainLife(You.instance(), 100, "You gain 100 life."));
		}
	}

	public AjaniMentorofHeroes(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Distribute three +1/+1 counters among one, two, or three target
		// creatures you control.
		this.addAbility(new AjaniMentorofHeroesAbility0(state));

		// +1: Look at the top four cards of your library. You may reveal an
		// Aura, creature, or planeswalker card from among them and put it into
		// your hand. Put the rest on the bottom of your library in any order.
		this.addAbility(new AjaniMentorofHeroesAbility1(state));

		// -8: You gain 100 life.
		this.addAbility(new AjaniMentorofHeroesAbility2(state));
	}
}
