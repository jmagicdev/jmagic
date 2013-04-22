package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kuldotha Phoenix")
@Types({Type.CREATURE})
@SubTypes({SubType.PHOENIX})
@ManaCost("2RRR")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class KuldothaPhoenix extends Card
{
	public static final class KuldothaPhoenixAbility1 extends ActivatedAbility
	{
		public KuldothaPhoenixAbility1(GameState state)
		{
			super(state, "(4): Return Kuldotha Phoenix from your graveyard to the battlefield. Activate this ability only during your upkeep and only if you control three or more artifacts.");
			this.setManaCost(new ManaPool("4"));

			EventFactory effect = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return Kuldotha Phoenix from your graveyard to the battlefield.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(effect);

			this.activateOnlyFromGraveyard();
			this.activateOnlyDuringYourUpkeep();
			this.addActivateRestriction(Not.instance(Metalcraft.instance()));
		}
	}

	public KuldothaPhoenix(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Metalcraft \u2014 (4): Return Kuldotha Phoenix from your graveyard to
		// the battlefield. Activate this ability only during your upkeep and
		// only if you control three or more artifacts.
		this.addAbility(new KuldothaPhoenixAbility1(state));
	}
}
