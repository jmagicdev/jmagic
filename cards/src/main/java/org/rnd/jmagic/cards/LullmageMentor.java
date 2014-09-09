package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lullmage Mentor")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.WIZARD})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class LullmageMentor extends Card
{
	public static final class CountermagicEqualsMerfolk extends EventTriggeredAbility
	{
		private static final class WhenYouCounterASpell implements EventPattern
		{
			@Override
			public boolean match(Event event, Identified object, GameState state)
			{
				if(event.type != EventType.COUNTER_ONE)
					return false;

				GameObject source = event.getSource();

				GameObject cause = event.parameters.get(EventType.Parameter.CAUSE).evaluate(state, source).getOne(GameObject.class);
				if(cause == null)
					return false;
				if(!cause.getController(state).equals(((Controllable)object).getController(state)))
					return false;

				GameObject countered = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, source).getOne(GameObject.class);
				return countered.isSpell();
			}

			@Override
			public boolean looksBackInTime()
			{
				return false;
			}

			@Override
			public boolean matchesManaAbilities()
			{
				return false;
			}
		}

		public CountermagicEqualsMerfolk(GameState state)
		{
			super(state, "Whenever a spell or ability you control counters a spell, you may put a 1/1 blue Merfolk creature token onto the battlefield.");

			this.addPattern(new WhenYouCounterASpell());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 blue Merfolk creature token onto the battlefield");
			token.setColors(Color.BLUE);
			token.setSubTypes(SubType.MERFOLK);
			this.addEffect(youMay(token.getEventFactory(), "You may put a 1/1 blue Merfolk creature token onto the battlefield."));
		}
	}

	public static final class MerfolkEqualsCountermagic extends ActivatedAbility
	{
		public MerfolkEqualsCountermagic(GameState state)
		{
			super(state, "Tap seven untapped Merfolk you control: Counter target spell.");

			SetGenerator yourMerfolk = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.MERFOLK));
			SetGenerator yourUntappedMerfolk = Intersect.instance(Untapped.instance(), yourMerfolk);

			EventFactory tapSevenMerfolk = new EventFactory(EventType.TAP_CHOICE, "Tap seven untapped Merfolk you control");
			tapSevenMerfolk.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapSevenMerfolk.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tapSevenMerfolk.parameters.put(EventType.Parameter.NUMBER, numberGenerator(7));
			tapSevenMerfolk.parameters.put(EventType.Parameter.CHOICE, yourUntappedMerfolk);
			this.addCost(tapSevenMerfolk);

			Target target = this.addTarget(Spells.instance(), "target spell");

			this.addEffect(Convenience.counter(targetedBy(target), "Counter target spell."));
		}
	}

	public LullmageMentor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever a spell or ability you control counters a spell, you may put
		// a 1/1 blue Merfolk creature token onto the battlefield.
		this.addAbility(new CountermagicEqualsMerfolk(state));

		// Tap seven untapped Merfolk you control: Counter target spell.
		this.addAbility(new MerfolkEqualsCountermagic(state));
	}
}
