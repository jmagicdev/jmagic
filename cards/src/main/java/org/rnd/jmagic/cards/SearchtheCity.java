package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Search the City")
@Types({Type.ENCHANTMENT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SearchtheCity extends Card
{
	public static final class SearchtheCityAbility0 extends EventTriggeredAbility
	{
		public SearchtheCityAbility0(GameState state)
		{
			super(state, "When Search the City enters the battlefield, exile the top five cards of your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory exile = exile(TopCards.instance(5, LibraryOf.instance(You.instance())), "Exile the top five cards of your library.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(SearchtheCityAbility1.class);
		}
	}

	public static final class SearchtheCityAbility1 extends EventTriggeredAbility
	{
		public SearchtheCityAbility1(GameState state)
		{
			super(state, "Whenever you play a card with the same name as one of the exiled cards, you may put one of those cards with that name into its owner's hand. Then if there are no cards exiled with Search the City, sacrifice it. If you do, take an extra turn after this one.");

			SetGenerator chosen = ChosenFor.instance(LinkedTo.instance(This.instance()));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(Cards.instance(), HasName.instance(NameOf.instance(chosen))));
			this.addPattern(pattern);

			EventFactory putIntoHand = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Put one of those cards with that name into its owner's hand.");
			putIntoHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putIntoHand.parameters.put(EventType.Parameter.PLAYER, You.instance());
			putIntoHand.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			putIntoHand.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(chosen, HasName.instance(NameOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT)))));
			this.addEffect(youMay(putIntoHand));

			this.addEffect(ifThen(Not.instance(chosen), ifThen(sacrificeThis("Search the City"), takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."), "If you do, take an extra turn after this one."), "Then if there are no cards exiled with Search the City, sacrifice it. If you do, take an extra turn after this one."));

			this.getLinkManager().addLinkClass(SearchtheCityAbility0.class);
		}
	}

	public SearchtheCity(GameState state)
	{
		super(state);

		// When Search the City enters the battlefield, exile the top five cards
		// of your library.
		this.addAbility(new SearchtheCityAbility0(state));

		// Whenever you play a card with the same name as one of the exiled
		// cards, you may put one of those cards with that name into its owner's
		// hand. Then if there are no cards exiled with Search the City,
		// sacrifice it. If you do, take an extra turn after this one.
		this.addAbility(new SearchtheCityAbility1(state));
	}
}
