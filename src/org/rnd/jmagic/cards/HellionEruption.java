package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hellion Eruption")
@Types({Type.SORCERY})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class HellionEruption extends Card
{
	public HellionEruption(GameState state)
	{
		super(state);

		// Sacrifice all creatures you control,
		EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice all creatures you control,");
		sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrifice.parameters.put(EventType.Parameter.PLAYER, You.instance());
		sacrifice.parameters.put(EventType.Parameter.PERMANENT, CREATURES_YOU_CONTROL);
		this.addEffect(sacrifice);

		// then put that many 4/4 red Hellion creature tokens onto the
		// battlefield.
		SetGenerator thatMany = Count.instance(OldObjectOf.instance(EffectResult.instance(sacrifice)));
		String effectName = "then put that many 4/4 red Hellion creature tokens onto the battlefield.";
		CreateTokensFactory tokens = new CreateTokensFactory(thatMany, numberGenerator(4), numberGenerator(4), effectName);
		tokens.setColors(Color.RED);
		tokens.setSubTypes(SubType.HELLION);
		this.addEffect(tokens.getEventFactory());
	}
}
