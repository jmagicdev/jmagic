package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Adventuring Gear")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({})
public final class AdventuringGear extends Card
{
	public static final class Hitchhiking extends EventTriggeredAbility
	{
		public Hitchhiking(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, equipped creature gets +2/+2 until end of turn.");

			this.addPattern(landfall());

			this.addEffect(ptChangeUntilEndOfTurn(EquippedBy.instance(ABILITY_SOURCE_OF_THIS), 2, 2, "Equipped creature gets +2/+2 until end of turn."));
		}
	}

	public AdventuringGear(GameState state)
	{
		super(state);

		this.addAbility(new Hitchhiking(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
