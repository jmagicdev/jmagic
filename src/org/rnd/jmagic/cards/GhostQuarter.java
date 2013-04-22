package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghost Quarter")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class GhostQuarter extends Card
{
	public static final class KillLand extends ActivatedAbility
	{
		public KillLand(GameState state)
		{
			super(state, "(T), Sacrifice Ghost Quarter: Destroy target land. Its controller may search his or her library for a basic land card, put it onto the battlefield, then shuffle his or her library.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Ghost Quarter"));

			Target target = this.addTarget(LandPermanents.instance(), "target land");
			this.addEffect(destroy(targetedBy(target), "Destroy target land."));
			SetGenerator controller = ControllerOf.instance(targetedBy(target));

			// Its controller may search his or her library for a basic land
			// card, put that card onto the battlefield tapped, then shuffle his
			// or her library.
			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Its controller searches his or her library for a basic land card, puts that card onto the battlefield tapped, then shuffles his or her library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, controller);
			search.parameters.put(EventType.Parameter.PLAYER, controller);
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));

			this.addEffect(playerMay(controller, search, "Its controller may search his or her library for a basic land card, put that card onto the battlefield tapped, then shuffle his or her library."));
		}
	}

	public GhostQuarter(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T), Sacrifice Ghost Quarter: Destroy target land. Its controller may
		// search his or her library for a basic land card, put it onto the
		// battlefield, then shuffle his or her library.
		this.addAbility(new KillLand(state));
	}
}
