package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Villainous Wealth")
@Types({Type.SORCERY})
@ManaCost("XBGU")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class VillainousWealth extends Card
{
	public VillainousWealth(GameState state)
	{
		super(state);

		// Target opponent exiles the top X cards of his or her library.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		SetGenerator X = ValueOfX.instance(This.instance());
		SetGenerator top = TopCards.instance(X, LibraryOf.instance(target));
		EventFactory exile = exile(top, "Target opponent exiles the top X cards of his or her library.");
		this.addEffect(exile);

		// You may cast any number of nonland cards with converted mana cost X
		// or less from among them without paying their mana costs.
		SetGenerator amongThem = Intersect.instance(EffectResult.instance(exile), HasConvertedManaCost.instance(Between.instance(null, X)));

		EventFactory cast = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast any number of nonland cards with converted mana cost X or less from among them without paying their mana costs.");
		cast.parameters.put(EventType.Parameter.CAUSE, This.instance());
		cast.parameters.put(EventType.Parameter.PLAYER, You.instance());
		cast.parameters.put(EventType.Parameter.OBJECT, amongThem);
		this.addEffect(cast);
	}
}
