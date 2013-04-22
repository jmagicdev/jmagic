package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sea of Sand")
@Types({Type.PLANE})
@SubTypes({SubType.RABIAH})
@ManaCost("")
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class SeaofSand extends Card
{
	public static final class RevealEachCard extends StaticAbility
	{
		public RevealEachCard(GameState state)
		{
			super(state, "Players reveal each card they draw.");

			EventPattern drawOneCard = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "Players reveal each card they draw", drawOneCard);

			SetGenerator originalEffect = EventParts.instance(replacement.replacedByThis());

			EventFactory drawAndReveal = new EventFactory(EventType.DRAW_AND_REVEAL, "Draw a card and reveal it");
			drawAndReveal.parameters.put(EventType.Parameter.EVENT, originalEffect);
			replacement.addEffect(drawAndReveal);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class DrawLandEqualsGain extends EventTriggeredAbility
	{
		public DrawLandEqualsGain(GameState state)
		{
			super(state, "Whenever a player draws a land card, that player gains 3 life.");

			SimpleEventPattern drawLand = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			drawLand.withResult(new ZoneChangeContaining(HasType.instance(Type.LAND)));
			this.addPattern(drawLand);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);
			this.addEffect(gainLife(thatPlayer, 3, "That player gains 3 life."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class DrawOtherEqualsPain extends EventTriggeredAbility
	{
		public DrawOtherEqualsPain(GameState state)
		{
			super(state, "Whenever a player draws a nonland card, that player loses 3 life.");

			SimpleEventPattern drawNonland = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			drawNonland.withResult(new ZoneChangeContaining(RelativeComplement.instance(Cards.instance(), HasType.instance(Type.LAND))));
			this.addPattern(drawNonland);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);
			this.addEffect(loseLife(thatPlayer, 3, "That player loses 3 life."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class CoverWithSand extends EventTriggeredAbility
	{
		public CoverWithSand(GameState state)
		{
			super(state, "Whenever you roll (C), put target permanent on top of its owner's library.");
			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(Permanents.instance(), "target permanent");

			EventFactory move = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target permanent on top of its owner's library.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			move.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
			this.addEffect(move);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public SeaofSand(GameState state)
	{
		super(state);

		// Players reveal each card they draw.
		this.addAbility(new RevealEachCard(state));

		// Whenever a player draws a land card, that player gains 3 life.
		this.addAbility(new DrawLandEqualsGain(state));

		// Whenever a player draws a nonland card, that player loses 3 life.
		this.addAbility(new DrawOtherEqualsPain(state));

		// Whenever you roll (C), put target permanent on top of its owner's
		// library.
		this.addAbility(new CoverWithSand(state));
	}
}
