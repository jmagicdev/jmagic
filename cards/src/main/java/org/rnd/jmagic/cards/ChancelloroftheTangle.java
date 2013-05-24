package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Chancellor of the Tangle")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4GGG")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ChancelloroftheTangle extends Card
{
	public static final class ChancelloroftheTangleAbility0 extends StaticAbility
	{
		public ChancelloroftheTangleAbility0(GameState state)
		{
			super(state, "You may reveal this card from your opening hand. If you do, at the beginning of your first main phase, add (G) to your mana pool.");
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
				SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_PHASE);
				pattern.put(EventType.Parameter.PHASE, MainPhaseOf.instance(You.instance()));

				EventFactory action = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "At the beginning of your first main phase, add (G) to your mana pool.");
				action.parameters.put(EventType.Parameter.CAUSE, This.instance());
				action.parameters.put(EventType.Parameter.EFFECT, Identity.instance(addManaToYourManaPoolFromAbility("(G)")));
				action.parameters.put(EventType.Parameter.EVENT, Identity.instance(pattern));

				part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(action));
			}

			this.addEffectPart(part);
		}
	}

	public ChancelloroftheTangle(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(7);

		// You may reveal this card from your opening hand. If you do, at the
		// beginning of your first main phase, add (G) to your mana pool.
		this.addAbility(new ChancelloroftheTangleAbility0(state));

		// Vigilance, reach
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
