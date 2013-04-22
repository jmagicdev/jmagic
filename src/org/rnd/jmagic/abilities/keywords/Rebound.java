package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/**
 * 702.85. Rebound
 * 
 * 702.85a Rebound appears on some instants and sorceries. It represents a
 * static ability that functions while the spell is on the stack and may create
 * a delayed triggered ability. "Rebound" means "If this spell was cast from
 * your hand, instead of putting it into your graveyard as it resolves, exile it
 * and, at the beginning of your next upkeep, you may cast this card from exile
 * without paying its mana cost."
 * 
 * 702.85b Casting a card without paying its mana cost as the result of a
 * rebound ability follows the rules for paying alternative costs in rules
 * 601.2b and 601.2e-g.
 * 
 * 702.85c Multiple instances of rebound on the same spell are redundant.
 */
public final class Rebound extends Keyword
{
	public Rebound(GameState state)
	{
		super(state, "Rebound");
	}

	public static final class ReboundAbility extends StaticAbility
	{
		public ReboundAbility(GameState state)
		{
			super(state, "If you cast this spell from your hand, exile it as it resolves. At the beginning of your next upkeep, you may cast this card from exile without paying its mana cost.");

			SetGenerator youCastThis = Intersect.instance(PlayerCasting.instance(This.instance()), You.instance());
			SetGenerator yourHand = HandOf.instance(You.instance());
			SetGenerator thisCastFromYourHand = Intersect.instance(ZoneCastFrom.instance(This.instance()), yourHand);
			SetGenerator youCastThisFromYourHand = Both.instance(youCastThis, thisCastFromYourHand);
			this.canApply = Both.instance(THIS_IS_ON_THE_STACK, youCastThisFromYourHand);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(state.game, "If this spell was cast from your hand, instead of putting it into your graveyard as it resolves, exile it and, at the beginning of your next upkeep, you may cast this card from exile without paying its mana cost.");
			replacement.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), This.instance(), true)
			{
				@Override
				public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state)
				{
					if(!zoneChange.isSpellResolution)
						return false;
					return super.match(zoneChange, thisObject, state);
				}
			});

			// Exile it
			replacement.changeDestination(ExileZone.instance());

			// Delayed trigger to cast it
			EventFactory castThisCard = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast this card from exile without paying its mana cost");
			castThisCard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			castThisCard.parameters.put(EventType.Parameter.PLAYER, You.instance());
			castThisCard.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(replacement.replacedByThis()));

			EventFactory trigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "At the beginning of your next upkeep, you may cast this card from exile without paying its mana cost");
			trigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
			trigger.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfYourUpkeep()));
			trigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(castThisCard));
			replacement.addEffect(trigger);

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	@Override
	protected java.util.List<org.rnd.jmagic.engine.StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new ReboundAbility(this.state));
	}
}
