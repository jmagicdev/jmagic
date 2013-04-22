package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Night Terrors")
@Types({Type.SORCERY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class NightTerrors extends Card
{
	public NightTerrors(GameState state)
	{
		super(state);

		// Target player reveals his or her hand. You choose a nonland card from
		// it. Exile that card.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		EventFactory reveal = reveal(HandOf.instance(target), "Target player reveals his or her hand.");
		this.addEffect(reveal);

		EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "You choose a nonland card from it. Exile that card.");
		exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
		exile.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(EffectResult.instance(reveal), HasType.instance(Type.LAND)));
		exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(exile);
	}
}
