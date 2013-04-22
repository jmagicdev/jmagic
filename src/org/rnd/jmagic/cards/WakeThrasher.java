package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Wake Thrasher")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.SOLDIER})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class WakeThrasher extends Card
{
	public static final class Thrash extends EventTriggeredAbility
	{
		public Thrash(GameState state)
		{
			super(state, "Whenever a permanent you control becomes untapped, Wake Thrasher gets +1/+1 until end of turn.");
			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator controller = ControllerOf.instance(thisCard);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.UNTAP_ONE_PERMANENT);
			pattern.put(EventType.Parameter.OBJECT, ControlledBy.instance(controller));
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+1), (+1), "Wake Thrasher gets +1/+1 until end of turn."));
		}
	}

	public WakeThrasher(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Thrash(state));
	}
}
