package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wrexial, the Risen Deep")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.KRAKEN})
@ManaCost("3UUB")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class WrexialtheRisenDeep extends Card
{
	/**
	 * @eparam CAUSE: The cause of this event
	 * @eparam OBJECT: What to cast for free and replace going to the graveyard
	 * with exiling.
	 * @eparam PLAYER: Who will be casting OBJECT
	 * @eparam RESULT: The cast card and the generated replacement effect
	 */
	public static final EventType CAST_FOR_FREE_AND_EXILE = new EventType("CAST_FOR_FREE_AND_EXILE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);

			java.util.Map<EventType.Parameter, Set> castParameters = new java.util.HashMap<EventType.Parameter, Set>();
			castParameters.put(Parameter.CAUSE, cause);
			castParameters.put(Parameter.PLAYER, parameters.get(Parameter.PLAYER));
			castParameters.put(Parameter.OBJECT, parameters.get(Parameter.OBJECT));
			Event cast = createEvent(game, "You may cast a card without paying its mana cost.", PLAY_WITHOUT_PAYING_MANA_COSTS, castParameters);
			if(!cast.perform(event, true))
				return false;

			SetGenerator aGraveyard = GraveyardOf.instance(Players.instance());
			Set thatCard = cast.getResult();

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(game, "If that card would be put into a graveyard this turn, exile it instead.");
			replacement.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(Stack.instance(), aGraveyard, cast.getResultGenerator(), true));
			replacement.changeDestination(ExileZone.instance());

			EventFactory replaceFactory = createFloatingReplacement(replacement, "If that card would be put into a graveyard this turn, exile it instead.");
			Event replace = replaceFactory.createEvent(game, event.getSource());
			if(!replace.perform(event, true))
				return false;

			Set result = Set.fromCollection(thatCard);
			result.retainAll(replace.getResult());
			event.setResult(result);
			return true;
		}
	};

	public static final class WrexialtheRisenDeepAbility1 extends EventTriggeredAbility
	{
		public WrexialtheRisenDeepAbility1(GameState state)
		{
			super(state, "Whenever Wrexial, the Risen Deep deals combat damage to a player, you may cast target instant or sorcery card from that player's graveyard without paying its mana cost. If that card would be put into a graveyard this turn, exile it instead.");
			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator instantOrSorcery = HasType.instance(Type.INSTANT, Type.SORCERY);
			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator cardsInThatPlayersGraveyard = InZone.instance(GraveyardOf.instance(thatPlayer));
			Target t = this.addTarget(Intersect.instance(instantOrSorcery, cardsInThatPlayersGraveyard), "target instant or sorcery card from that player's graveyard");

			EventFactory effect = new EventFactory(CAST_FOR_FREE_AND_EXILE, "You may cast target instant or sorcery card from that player's graveyard without paying its mana cost. If that card would be put into a graveyard this turn, exile it instead.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, targetedBy(t));
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(effect);
		}
	}

	public WrexialtheRisenDeep(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(8);

		// Islandwalk, swampwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));

		// Whenever Wrexial, the Risen Deep deals combat damage to a player, you
		// may cast target instant or sorcery card from that player's graveyard
		// without paying its mana cost. If that card would be put into a
		// graveyard this turn, exile it instead.
		this.addAbility(new WrexialtheRisenDeepAbility1(state));
	}
}
