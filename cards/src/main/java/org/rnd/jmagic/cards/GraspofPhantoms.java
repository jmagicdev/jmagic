package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grasp of Phantoms")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class GraspofPhantoms extends Card
{
	public GraspofPhantoms(GameState state)
	{
		super(state);

		// Put target creature on top of its owner's library.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		EventFactory put = new EventFactory(EventType.MOVE_OBJECTS, "Put target creature on top of its owner's library.");
		put.parameters.put(EventType.Parameter.CAUSE, This.instance());
		put.parameters.put(EventType.Parameter.TO, LibraryOf.instance(OwnerOf.instance(target)));
		put.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(put);

		// Flashback (7)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(7)(U)"));
	}
}
