package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("River of Tears")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class RiverofTears extends Card
{
	public static final class TearyMana extends ActivatedAbility
	{
		public TearyMana(GameState state)
		{
			super(state, "(T): Add (U) to your mana pool. If you played a land this turn, add (B) to your mana pool instead.");
			this.costsTap = true;

			state.ensureTracker(new PlayedALandThisTurn.Tracker());
			SetGenerator mana = IfThenElse.instance(PlayedALandThisTurn.instance(You.instance()), Identity.fromCollection(new ManaPool("B")), Identity.fromCollection(new ManaPool("U")));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			parameters.put(EventType.Parameter.MANA, mana);
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.ADD_MANA, parameters, "Add (U) to your mana pool. If you played a land this turn, add (B) to your mana pool instead."));
		}
	}

	public RiverofTears(GameState state)
	{
		super(state);

		// (T): Add (U) to your mana pool. If you played a land this turn, add
		// (B) to your mana pool instead.
		this.addAbility(new TearyMana(state));
	}
}
