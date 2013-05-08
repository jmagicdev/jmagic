package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Increasing Savagery")
@Types({Type.SORCERY})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class IncreasingSavagery extends Card
{
	public IncreasingSavagery(GameState state)
	{
		super(state);

		// Put five +1/+1 counters on target creature. If Increasing Savagery
		// was cast from a graveyard, put ten +1/+1 counters on that creature
		// instead.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator number = IfThenElse.instance(Intersect.instance(ZoneCastFrom.instance(This.instance()), GraveyardOf.instance(Players.instance())), numberGenerator(10), numberGenerator(5));

		this.addEffect(putCounters(number, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put five +1/+1 counters on target creature. If Increasing Savagery was cast from a graveyard, put ten +1/+1 counters on that creature instead."));

		// Flashback (5)(G)(G) (You may cast this card from your graveyard for
		// its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(5)(G)(G)"));
	}
}
