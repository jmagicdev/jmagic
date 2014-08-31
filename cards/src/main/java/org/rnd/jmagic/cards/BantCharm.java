package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bant Charm")
@Types({Type.INSTANT})
@ManaCost("GWU")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class BantCharm extends Card
{
	public BantCharm(GameState state)
	{
		super(state);

		{
			// Destroy target artifact;
			Target target = this.addTarget(1, ArtifactPermanents.instance(), "target artifact");
			this.addEffect(1, destroy(targetedBy(target), "Destroy target artifact."));
		}

		{
			// or put target creature on the bottom of its owner's library;
			Target target = this.addTarget(2, CreaturePermanents.instance(), "target creature");
			EventFactory move = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target creature on the bottom of its owner's library.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(2, move);
		}

		{
			// or counter target instant spell.
			Target target = this.addTarget(3, Intersect.instance(HasType.instance(Type.INSTANT), Spells.instance()), "target spell");
			this.addEffect(3, counter(targetedBy(target), "Counter target instant spell."));
		}
	}
}
