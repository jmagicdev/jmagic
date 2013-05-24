package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chancellor of the Spires")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("4UUU")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ChancelloroftheSpires extends Card
{
	public static final class ChancelloroftheSpiresAbility0 extends StaticAbility
	{
		public ChancelloroftheSpiresAbility0(GameState state)
		{
			super(state, "You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, each opponent puts the top seven cards of his or her library into his or her graveyard.");
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
				EventFactory action = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "At the beginning of the first upkeep, each opponent puts the top seven cards of his or her library into his or her graveyard.");
				action.parameters.put(EventType.Parameter.CAUSE, This.instance());
				action.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfEachUpkeep()));
				action.parameters.put(EventType.Parameter.EFFECT, Identity.instance(millCards(OpponentsOf.instance(You.instance()), 7, "Each opponent puts the top seven cards of his or her library into his or her graveyard.")));

				part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(action));
			}

			this.addEffectPart(part);
		}
	}

	public static final class ChancelloroftheSpiresAbility2 extends EventTriggeredAbility
	{
		public ChancelloroftheSpiresAbility2(GameState state)
		{
			super(state, "When Chancellor of the Spires enters the battlefield, you may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(OpponentsOf.instance(You.instance())))), "target instant or sorcery card in an opponent's graveyard"));

			EventFactory effect = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(effect);
		}
	}

	public ChancelloroftheSpires(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(7);

		// You may reveal this card from your opening hand. If you do, at the
		// beginning of the first upkeep, each opponent puts the top seven cards
		// of his or her library into his or her graveyard.
		this.addAbility(new ChancelloroftheSpiresAbility0(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Chancellor of the Spires enters the battlefield, you may cast
		// target instant or sorcery card from an opponent's graveyard without
		// paying its mana cost.
		this.addAbility(new ChancelloroftheSpiresAbility2(state));
	}
}
