package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chancellor of the Forge")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4RRR")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ChancelloroftheForge extends Card
{
	public static final class ChancelloroftheForgeAbility0 extends StaticAbility
	{
		public ChancelloroftheForgeAbility0(GameState state)
		{
			super(state, "You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, put a 1/1 red Goblin creature token with haste onto the battlefield.");
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
				EventFactory action = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "At the beginning of the first upkeep, put a 1/1 red Goblin creature token with haste onto the battlefield.");
				action.parameters.put(EventType.Parameter.CAUSE, This.instance());
				action.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfEachUpkeep()));

				{
					CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 red Goblin creature token with Haste onto the battlefield.");
					factory.setColors(Color.RED);
					factory.setSubTypes(SubType.GOBLIN);
					factory.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
					action.parameters.put(EventType.Parameter.EFFECT, Identity.instance(factory.getEventFactory()));
				}

				part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(action));
			}

			this.addEffectPart(part);
		}
	}

	public static final class ChancelloroftheForgeAbility1 extends EventTriggeredAbility
	{
		public ChancelloroftheForgeAbility1(GameState state)
		{
			super(state, "When Chancellor of the Forge enters the battlefield, put X 1/1 red Goblin creature tokens with haste onto the battlefield, where X is the number of creatures you control.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator X = Count.instance(CREATURES_YOU_CONTROL);
			CreateTokensFactory factory = new CreateTokensFactory(X, "Put a 1/1 red Goblin creature token with Haste onto the battlefield.");
			factory.addCreature(1, 1);
			factory.setColors(Color.RED);
			factory.setSubTypes(SubType.GOBLIN);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public ChancelloroftheForge(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// You may reveal this card from your opening hand. If you do, at the
		// beginning of the first upkeep, put a 1/1 red Goblin creature token
		// with haste onto the battlefield.
		this.addAbility(new ChancelloroftheForgeAbility0(state));

		// When Chancellor of the Forge enters the battlefield, put X 1/1 red
		// Goblin creature tokens with haste onto the battlefield, where X is
		// the number of creatures you control.
		this.addAbility(new ChancelloroftheForgeAbility1(state));
	}
}
