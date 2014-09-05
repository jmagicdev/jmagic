package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mind Spring")
@Types({Type.SORCERY})
@ManaCost("XUU")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = Morningtide.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MindSpring extends Card
{
	public MindSpring(GameState state)
	{
		super(state);

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
		this.addEffect(new EventFactory(EventType.DRAW_CARDS, parameters, "Draw X cards."));
	}
}
