package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vigil for the Lost")
@Types({Type.ENCHANTMENT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class VigilfortheLost extends Card
{
	public static final class VigilfortheLostAbility0 extends EventTriggeredAbility
	{
		public VigilfortheLostAbility0(GameState state)
		{
			super(state, "Whenever a creature you control dies, you may pay (X). If you do, you gain X life.");

			this.addPattern(whenXDies(CREATURES_YOU_CONTROL));

			EventFactory mayPayX = new EventFactory(EventType.PLAYER_MAY_PAY_X, "You may pay (X).");
			mayPayX.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPayX.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(mayPayX);

			this.addEffect(gainLife(You.instance(), ValueOfX.instance(This.instance()), "If you do, you gain X life."));
		}
	}

	public VigilfortheLost(GameState state)
	{
		super(state);

		// Whenever a creature you control is put into a graveyard from the
		// battlefield, you may pay (X). If you do, you gain X life.
		this.addAbility(new VigilfortheLostAbility0(state));
	}
}
