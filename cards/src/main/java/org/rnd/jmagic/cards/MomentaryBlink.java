package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Momentary Blink")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class MomentaryBlink extends Card
{
	public MomentaryBlink(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_YOU_CONTROL, "target creature you control");

		EventType.ParameterMap blinkParameters = new EventType.ParameterMap();
		blinkParameters.put(EventType.Parameter.CAUSE, This.instance());
		blinkParameters.put(EventType.Parameter.TARGET, targetedBy(target));
		blinkParameters.put(EventType.Parameter.PLAYER, OwnerOf.instance(targetedBy(target)));
		this.addEffect(new EventFactory(BLINK, blinkParameters, "Exile target creature you control, then return it to the battlefield under its owner's control."));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(3)(U)"));
	}
}
