package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("The Tabernacle at Pendrell Vale")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Legends.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class TheTabernacleatPendrellVale extends Card
{
	public static final class ThisCreatureKindOfSucksNow extends EventTriggeredAbility
	{
		public ThisCreatureKindOfSucksNow(GameState state)
		{
			super(state, "At the beginning of your upkeep, destroy this creature unless you pay (1).");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory destroy = destroy(ABILITY_SOURCE_OF_THIS, "Destroy this creature");

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (1)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
			pay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(unless(You.instance(), destroy, pay, "Destroy this creature unless you pay (1)."));
		}
	}

	public static final class CreaturesKindOfSuckNow extends StaticAbility
	{
		public CreaturesKindOfSuckNow(GameState state)
		{
			super(state, "All creatures have \"At the beginning of your upkeep, destroy this creature unless you pay (1).\"");
			this.addEffectPart(addAbilityToObject(CreaturePermanents.instance(), ThisCreatureKindOfSucksNow.class));
		}
	}

	public TheTabernacleatPendrellVale(GameState state)
	{
		super(state);

		// All creatures have
		// "At the beginning of your upkeep, destroy this creature unless you pay (1)."
		this.addAbility(new CreaturesKindOfSuckNow(state));
	}
}
