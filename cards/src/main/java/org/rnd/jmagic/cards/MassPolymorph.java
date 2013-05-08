package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mass Polymorph")
@Types({Type.SORCERY})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MassPolymorph extends Card
{
	public MassPolymorph(GameState state)
	{
		super(state);

		// Exile all creatures you control,
		EventFactory exile = exile(CREATURES_YOU_CONTROL, "Exile all creatures you control,");
		this.addEffect(exile);

		// then reveal cards from the top of your library until you reveal that
		// many creature cards.
		SetGenerator thatMany = Count.instance(EffectResult.instance(exile));
		SetGenerator yourLibrary = LibraryOf.instance(You.instance());
		SetGenerator toReveal = TopMost.instance(yourLibrary, thatMany, HasType.instance(Type.CREATURE));
		EventFactory reveal = new EventFactory(EventType.REVEAL, "then reveal cards from the top of your library until you reveal that many creature cards.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, toReveal);
		this.addEffect(reveal);

		// Put all creature cards revealed this way onto the battlefield,
		SetGenerator revealedCards = EffectResult.instance(reveal);
		SetGenerator creaturesRevealed = Intersect.instance(revealedCards, HasType.instance(Type.CREATURE));
		EventFactory drop = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put all creature cards revealed this way onto the battlefield,");
		drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
		drop.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		drop.parameters.put(EventType.Parameter.OBJECT, creaturesRevealed);
		this.addEffect(drop);

		// then shuffle the rest of the revealed cards into your library.
		SetGenerator restOfTheRevealedCards = RelativeComplement.instance(revealedCards, HasType.instance(Type.CREATURE));
		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "then shuffle the rest of the revealed cards into your library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(restOfTheRevealedCards, You.instance()));
		this.addEffect(shuffle);
	}
}
