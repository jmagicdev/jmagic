package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Aven Mindcensor")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.BIRD})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AvenMindcensor extends Card
{
	public static final class NerfSearches extends StaticAbility
	{
		public NerfSearches(GameState state)
		{
			super(state, "If an opponent would search a library, that player searches the top four cards of that library instead.");

			SimpleEventPattern opponentSearches = new SimpleEventPattern(EventType.SEARCH_MARKER);
			opponentSearches.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			opponentSearches.put(EventType.Parameter.CARD, LibraryOf.instance(Players.instance()));
			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If an opponent would search a library, that player searches the top four cards of that library instead", opponentSearches);

			SetGenerator searching = EventParameter.instance(replacement.replacedByThis(), EventType.Parameter.CARD);
			SetGenerator library = Intersect.instance(searching, LibraryOf.instance(Players.instance()));
			SetGenerator topFour = TopCards.instance(4, library);

			EventFactory newSearch = new EventFactory(EventType.SEARCH_MARKER, "That player searches the top four cards of that library");
			newSearch.parameters.put(EventType.Parameter.CARD, topFour);
			replacement.addEffect(newSearch);

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public AvenMindcensor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// If an opponent would search a library, that player searches the top
		// four cards of that library instead.
		this.addAbility(new NerfSearches(state));
	}
}
