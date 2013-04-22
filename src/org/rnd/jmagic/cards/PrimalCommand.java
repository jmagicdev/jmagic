package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Primal Command")
@Types({Type.SORCERY})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class PrimalCommand extends Card
{
	public PrimalCommand(GameState state)
	{
		super(state);

		// Choose two \u2014
		this.setNumModes(new Set(new org.rnd.util.NumberRange(2, 2)));

		// Target player gains 7 life;
		SetGenerator target1 = targetedBy(this.addTarget(1, Players.instance(), "target player to gain 7 life"));
		this.addEffect(1, gainLife(target1, 7, "Target player gains 7 life"));

		// or put target noncreature permanent on top of its owner's library;
		SetGenerator nonCreaturePermanents = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.CREATURE));
		SetGenerator target2 = targetedBy(this.addTarget(2, nonCreaturePermanents, "target noncreature permanent"));
		EventFactory putOnTop = new EventFactory(EventType.MOVE_OBJECTS, "put target noncreature permanent on top of its owner's library");
		putOnTop.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putOnTop.parameters.put(EventType.Parameter.TO, LibraryOf.instance(OwnerOf.instance(target2)));
		putOnTop.parameters.put(EventType.Parameter.OBJECT, target2);
		putOnTop.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		this.addEffect(2, putOnTop);

		// or target player shuffles his or her graveyard into his or her
		// library;
		SetGenerator target3 = targetedBy(this.addTarget(3, Players.instance(), "target player to shuffle his or her graveyard into his or her library"));
		EventFactory shuffleGraveyard = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "target player shuffles his or her graveyard into his or her library");
		shuffleGraveyard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffleGraveyard.parameters.put(EventType.Parameter.OBJECT, Union.instance(InZone.instance(GraveyardOf.instance(target3)), target3));
		this.addEffect(3, shuffleGraveyard);

		// or search your library for a creature card, reveal it, put it into
		// your hand, then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "search your library for a creature card, reveal it, put it into your hand, then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.CREATURE)));
		this.addEffect(4, search);
	}
}
