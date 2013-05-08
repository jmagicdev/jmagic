package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Dredge extends Keyword
{
	private final int N;

	public Dredge(GameState state, int N)
	{
		super(state, "Dredge " + N);
		this.N = N;
	}

	@Override
	public Dredge create(Game game)
	{
		return new Dredge(game.physicalState, this.N);
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();

		ret.add(new DredgeAbility(this.state, this.N));

		return ret;
	}

	public static final class DredgeAbility extends StaticAbility
	{
		private final int N;

		public DredgeAbility(GameState state, int N)
		{
			super(state, "If you would draw a card, instead you may put exactly " + org.rnd.util.NumberNames.get(N) + " card" + (N == 1 ? "" : "s") + " from the top of your library into your graveyard. If you do, return this card from your graveyard to your hand. Otherwise, draw a card.");
			this.N = N;

			SetGenerator thisCard = This.instance();
			SetGenerator owner = OwnerOf.instance(thisCard);
			SetGenerator ownersGraveyard = GraveyardOf.instance(owner);
			SetGenerator ownersLibrary = LibraryOf.instance(owner);
			SetGenerator ownersHand = HandOf.instance(owner);

			// Replace draw events for this cards owner
			SimpleEventPattern replaceMe = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			replaceMe.put(EventType.Parameter.PLAYER, owner);

			EventReplacementEffect dredgeEffect = new EventReplacementEffect(this.game, "You may instead put " + N + " card" + (N == 1 ? "" : "s") + " from the top of your library into your graveyard and return this card from your graveyard to your hand.", replaceMe);
			dredgeEffect.makeOptional(owner);

			SetGenerator cause = EventParameter.instance(dredgeEffect.replacedByThis(), EventType.Parameter.CAUSE);

			// Move the cards from library to graveyard
			EventFactory millFactory = new EventFactory(EventType.MOVE_OBJECTS, ("You may instead put " + N + " card" + (N == 1 ? "" : "s") + " from the top of your library into your graveyard"));
			millFactory.parameters.put(EventType.Parameter.CAUSE, cause);
			millFactory.parameters.put(EventType.Parameter.TO, ownersGraveyard);
			millFactory.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(N, ownersLibrary));
			dredgeEffect.addEffect(millFactory);

			// "Draw" the card from the graveyard
			EventFactory handFactory = new EventFactory(EventType.MOVE_OBJECTS, "Return this card from your graveyard to your hand");
			handFactory.parameters.put(EventType.Parameter.CAUSE, cause);
			handFactory.parameters.put(EventType.Parameter.TO, ownersHand);
			handFactory.parameters.put(EventType.Parameter.OBJECT, thisCard);
			dredgeEffect.addEffect(handFactory);

			this.addEffectPart(replacementEffectPart(dredgeEffect));

			SetGenerator cardsInYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator thisIsInYourGraveyard = Intersect.instance(This.instance(), cardsInYourGraveyard);

			SetGenerator librarySize = Count.instance(InZone.instance(LibraryOf.instance(You.instance())));
			SetGenerator nCardsInLibrary = Intersect.instance(librarySize, Between.instance(this.N, null));

			this.canApply = Both.instance(thisIsInYourGraveyard, nCardsInLibrary);
		}

		@Override
		public DredgeAbility create(Game game)
		{
			return new DredgeAbility(game.physicalState, this.N);
		}
	}
}
