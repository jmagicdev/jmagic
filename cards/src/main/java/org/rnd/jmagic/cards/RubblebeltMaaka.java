package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rubblebelt Maaka")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = DragonsMaze.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RubblebeltMaaka extends Card
{
	public static final class RubblebeltMaakaAbility0 extends ActivatedAbility
	{
		public RubblebeltMaakaAbility0(GameState state)
		{
			super(state, "(R), Discard Rubblebelt Maaka: Target attacking creature gets +3/+3 until end of turn.");
			this.setManaCost(new ManaPool("R"));

			EventFactory discardFactory = new EventFactory(EventType.DISCARD_CARDS, "Discard Rubblebelt Maaka");
			discardFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			discardFactory.parameters.put(EventType.Parameter.CARD, ABILITY_SOURCE_OF_THIS);
			this.addCost(discardFactory);

			SetGenerator target = targetedBy(this.addTarget(Attacking.instance(), "target attacking creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, +3, +3, "Target attacking creature gets +3/+3 until end of turn."));
		}
	}

	public RubblebeltMaaka(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Bloodrush — {R}, Discard Rubblebelt Maaka: Target attacking creature
		// gets +3/+3 until end of turn.
		this.addAbility(new RubblebeltMaakaAbility0(state));
	}
}
