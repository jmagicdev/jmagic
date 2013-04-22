package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Knowledge Pool")
@Types({Type.ARTIFACT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({})
public final class KnowledgePool extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Knowledge Pool", "Choose another nonland card exiled with Knowledge Pool to cast without paying that card's mana cost.", true);

	public static final class KnowledgePoolAbility0 extends EventTriggeredAbility
	{
		public KnowledgePoolAbility0(GameState state)
		{
			super(state, "Imprint \u2014 When Knowledge Pool enters the battlefield, each player exiles the top three cards of his or her library.");
			this.addPattern(whenThisEntersTheBattlefield());

			DynamicEvaluation dynamicPlayer = DynamicEvaluation.instance();
			SetGenerator library = LibraryOf.instance(dynamicPlayer);

			EventFactory exile = new EventFactory(EventType.MOVE_OBJECTS, "That player exiles the top three cards of his or her library");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			exile.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(3, library));
			exile.setLink(this);

			EventFactory eachPlayer = new EventFactory(FOR_EACH_PLAYER, "Each player exiles the top three cards of his or her library");
			eachPlayer.parameters.put(EventType.Parameter.TARGET, Identity.instance(dynamicPlayer));
			eachPlayer.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));
			this.addEffect(eachPlayer);

			this.getLinkManager().addLinkClass(KnowledgePoolAbility1.class);
		}
	}

	public static final class KnowledgePoolAbility1 extends EventTriggeredAbility
	{
		private static final class CastFromHandPattern implements EventPattern
		{
			@Override
			public boolean match(Event event, Identified object, GameState state)
			{
				if(!EventType.BECOMES_PLAYED.equals(event.type))
					return false;
				GameObject cast = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, event.getSource()).getOne(GameObject.class);
				if(!(cast.isSpell()))
					return false;
				if(cast.getOwner(state).getHandID() != cast.zoneCastFrom)
					return false;
				return true;
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

		public KnowledgePoolAbility1(GameState state)
		{
			super(state, "Whenever a player casts a spell from his or her hand, that player exiles it. If the player does, he or she may cast another nonland card exiled with Knowledge Pool without paying that card's mana cost.");

			this.addPattern(new CastFromHandPattern());

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator thatPlayer = EventParameter.instance(triggerEvent, EventType.Parameter.PLAYER);
			SetGenerator it = EventParameter.instance(triggerEvent, EventType.Parameter.OBJECT);

			EventFactory exile = exile(it, "That player exiles it");
			exile.setLink(this);

			SetGenerator exiledCards = ChosenFor.instance(Union.instance(This.instance(), LinkedTo.instance(This.instance())));
			SetGenerator nonlandExiledCards = RelativeComplement.instance(exiledCards, HasType.instance(Type.LAND));
			SetGenerator anotherNonlandExiledCards = RelativeComplement.instance(nonlandExiledCards, NewObjectOf.instance(EffectResult.instance(exile)));

			EventFactory choice = new EventFactory(EventType.PLAYER_CHOOSE, "");
			choice.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			choice.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			choice.parameters.put(EventType.Parameter.CHOICE, anotherNonlandExiledCards);
			choice.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, REASON));

			EventFactory cast = new EventFactory(EventType.PLAY_CARD, "Cast another nonland card exiled with Knowledge Pool without paying that card's mana cost");
			cast.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cast.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			cast.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(choice));
			cast.parameters.put(EventType.Parameter.ALTERNATE_COST, Empty.instance());

			EventFactory playerMayCast = playerMay(thatPlayer, sequence(choice, cast), "He or she may cast another nonland card exiled with Knowledge Pool without paying that card's mana cost");

			EventFactory ifThen = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "That player exiles it. If the player does, he or she may cast another nonland card exiled with Knowledge Pool without paying that card's mana cost.");
			ifThen.parameters.put(EventType.Parameter.IF, Identity.instance(exile));
			ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(playerMayCast));
			this.addEffect(ifThen);

			this.getLinkManager().addLinkClass(KnowledgePoolAbility0.class);
		}
	}

	public KnowledgePool(GameState state)
	{
		super(state);

		// Imprint \u2014 When Knowledge Pool enters the battlefield, each
		// player exiles the top three cards of his or her library.
		this.addAbility(new KnowledgePoolAbility0(state));

		// Whenever a player casts a spell from his or her hand, that player
		// exiles it. If the player does, he or she may cast another nonland
		// card exiled with Knowledge Pool without paying that card's mana cost.
		this.addAbility(new KnowledgePoolAbility1(state));
	}
}
