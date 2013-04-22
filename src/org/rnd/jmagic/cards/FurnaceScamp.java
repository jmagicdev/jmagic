package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Furnace Scamp")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FurnaceScamp extends Card
{
	public static final class FurnaceScampAbility0 extends EventTriggeredAbility
	{
		public FurnaceScampAbility0(GameState state)
		{
			super(state, "Whenever Furnace Scamp deals combat damage to a player, you may sacrifice it. If you do, Furnace Scamp deals 3 damage to that player.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may sacrifice it. If you do, Furnace Scamp deals 3 damage to that player.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(sacrificeThis("Furnace Scamp"), "You may sacrifice it.")));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(permanentDealDamage(3, TakerOfDamage.instance(TriggerDamage.instance(This.instance())), "Furnace Scamp deals 3 damage to that player.")));
			this.addEffect(factory);
		}
	}

	public FurnaceScamp(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Furnace Scamp deals combat damage to a player, you may
		// sacrifice it. If you do, Furnace Scamp deals 3 damage to that player.
		this.addAbility(new FurnaceScampAbility0(state));
	}
}
