package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Karmic Justice")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class KarmicJustice extends Card
{
	public static final class KarmicJusticeAbility0 extends EventTriggeredAbility
	{
		public KarmicJusticeAbility0(GameState state)
		{
			super(state, "Whenever a spell or ability an opponent controls destroys a noncreature permanent you control, you may destroy target permanent that opponent controls.");

			SetGenerator yourStuff = RelativeComplement.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE));

			SimpleEventPattern opponentsKillStuff = new SimpleEventPattern(EventType.DESTROY_ONE_PERMANENT);
			opponentsKillStuff.put(EventType.Parameter.CAUSE, ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance()));
			opponentsKillStuff.put(EventType.Parameter.PERMANENT, yourStuff);
			this.addPattern(opponentsKillStuff);

			SetGenerator thatSpell = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.CAUSE);
			SetGenerator theirStuff = ControlledBy.instance(ControllerOf.instance(thatSpell));
			SetGenerator target = targetedBy(this.addTarget(theirStuff, "target permanent that opponent controls"));

			EventFactory destroy = destroy(target, "Destroy target permanent that opponent controls");
			this.addEffect(youMay(destroy, "You may destroy target permanent that opponent controls."));
		}
	}

	public KarmicJustice(GameState state)
	{
		super(state);

		// Whenever a spell or ability an opponent controls destroys a
		// noncreature permanent you control, you may destroy target permanent
		// that opponent controls.
		this.addAbility(new KarmicJusticeAbility0(state));
	}
}
