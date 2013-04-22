package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mind Shatter")
@Types({Type.SORCERY})
@ManaCost("XBB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class MindShatter extends Card
{
	public MindShatter(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
		this.addEffect(new EventFactory(EventType.DISCARD_RANDOM, parameters, "Target player discards X cards at random."));
	}
}
