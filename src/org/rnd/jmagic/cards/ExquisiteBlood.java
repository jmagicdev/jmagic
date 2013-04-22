package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Exquisite Blood")
@Types({Type.ENCHANTMENT})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class ExquisiteBlood extends Card
{
	public static final class ExquisiteBloodAbility0 extends EventTriggeredAbility
	{
		public ExquisiteBloodAbility0(GameState state)
		{
			super(state, "Whenever an opponent loses life, you gain that much life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.LOSE_LIFE);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			this.addPattern(pattern);

			SetGenerator thatMuch = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.NUMBER);

			this.addEffect(gainLife(You.instance(), thatMuch, "You gain that much life."));
		}
	}

	public ExquisiteBlood(GameState state)
	{
		super(state);

		// Whenever an opponent loses life, you gain that much life.
		this.addAbility(new ExquisiteBloodAbility0(state));
	}
}
