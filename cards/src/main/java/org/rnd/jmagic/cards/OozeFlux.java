package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ooze Flux")
@Types({Type.ENCHANTMENT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class OozeFlux extends Card
{
	public static final class OozeFluxAbility0 extends ActivatedAbility
	{
		public OozeFluxAbility0(GameState state)
		{
			super(state, "(1)(G), Remove one or more +1/+1 counters from among creatures you control: Put an X/X green Ooze creature token onto the battlefield, where X is the number of +1/+1 counters removed this way.");
			this.setManaCost(new ManaPool("(1)(G)"));

			EventFactory remove = new EventFactory(EventType.REMOVE_COUNTERS_CHOICE, "Remove one or more +1/+1 counters from among creatures you control");
			remove.parameters.put(EventType.Parameter.CAUSE, This.instance());
			remove.parameters.put(EventType.Parameter.COUNTER, CountersOn.instance(CREATURES_YOU_CONTROL));
			remove.parameters.put(EventType.Parameter.NUMBER, Between.instance(1, null));
			remove.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addCost(remove);

			SetGenerator amount = Count.instance(CostResult.instance(remove));
			CreateTokensFactory token = new CreateTokensFactory(numberGenerator(1), amount, amount, "Put an X/X green Ooze creature token onto the battlefield, where X is the number of +1/+1 counters removed this way.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.OOZE);
			this.addEffect(token.getEventFactory());
		}
	}

	public OozeFlux(GameState state)
	{
		super(state);

		// (1)(G), Remove one or more +1/+1 counters from among creatures you
		// control: Put an X/X green Ooze creature token onto the battlefield,
		// where X is the number of +1/+1 counters removed this way.
		this.addAbility(new OozeFluxAbility0(state));
	}
}
