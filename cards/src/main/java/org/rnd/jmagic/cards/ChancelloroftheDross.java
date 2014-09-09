package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chancellor of the Dross")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("4BBB")
@ColorIdentity({Color.BLACK})
public final class ChancelloroftheDross extends Card
{
	public static final class ChancelloroftheDrossAbility0 extends StaticAbility
	{
		public ChancelloroftheDrossAbility0(GameState state)
		{
			super(state, "You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, each opponent loses 3 life, then you gain life equal to the life lost this way.");
			this.canApply = NonEmpty.instance();

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BEGIN_THE_GAME_EFFECT);

			{
				EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal this card from your opening hand.");
				reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
				reveal.parameters.put(EventType.Parameter.OBJECT, This.instance());
				reveal.parameters.put(EventType.Parameter.EFFECT, Identity.instance(CurrentTurn.instance()));
				part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(reveal));
			}

			{
				EventFactory action = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "At the beginning of the first upkeep, each opponent loses 3 life, then you gain life equal to the life lost this way.");
				action.parameters.put(EventType.Parameter.CAUSE, This.instance());
				action.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfEachUpkeep()));

				{
					EventFactory lose = loseLife(OpponentsOf.instance(You.instance()), 3, "Each opponent loses 3 life,");
					EventFactory gain = gainLife(You.instance(), EffectResult.instance(lose), "then you gain life equal to the life lost this way.");
					action.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sequence(lose, gain)));
				}

				part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(action));
			}

			this.addEffectPart(part);
		}
	}

	public ChancelloroftheDross(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// You may reveal this card from your opening hand. If you do, at the
		// beginning of the first upkeep, each opponent loses 3 life, then you
		// gain life equal to the life lost this way.
		this.addAbility(new ChancelloroftheDrossAbility0(state));

		// Flying, lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
