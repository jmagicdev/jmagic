package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Disciple of Deceit")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DiscipleofDeceit extends Card
{
	public static final class DiscipleofDeceitAbility0 extends EventTriggeredAbility
	{
		public DiscipleofDeceitAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Disciple of Deceit becomes untapped, you may discard a nonland card. If you do, search your library for a card with the same converted mana cost as that card, reveal it, put it into your hand, then shuffle your library.");
			this.addPattern(inspired());

			SetGenerator nonlands = RelativeComplement.instance(InZone.instance(HandOf.instance(You.instance())), HasType.instance(Type.LAND));
			EventFactory discard = discardCards(You.instance(), 1, nonlands, "Discard a nonland card");

			SetGenerator discarded = NewObjectOf.instance(EffectResult.instance(discard));
			EventFactory search = new EventFactory(EventType.SEARCH, "Search your library for a card with the same converted mana cost as that card, reveal it,");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.CARD, LibraryOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasConvertedManaCost.instance(ConvertedManaCostOf.instance(discarded))));

			EventFactory toHand = putIntoHand(EffectResult.instance(search), You.instance(), "put it into your hand,");
			EventFactory shuffle = shuffleLibrary(You.instance(), "then shuffle your library.");

			this.addEffect(ifThen(youMay(discard), sequence(search, toHand, shuffle), "You may discard a nonland card. If you do, search your library for a card with the same converted mana cost as that card, reveal it, put it into your hand, then shuffle your library."));
		}
	}

	public DiscipleofDeceit(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Inspired \u2014 Whenever Disciple of Deceit becomes untapped, you may
		// discard a nonland card. If you do, search your library for a card
		// with the same converted mana cost as that card, reveal it, put it
		// into your hand, then shuffle your library.
		this.addAbility(new DiscipleofDeceitAbility0(state));
	}
}
