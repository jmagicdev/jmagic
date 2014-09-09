package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nyx-Fleece Ram")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SHEEP})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class NyxFleeceRam extends Card
{
	public static final class NyxFleeceRamAbility0 extends EventTriggeredAbility
	{
		public NyxFleeceRamAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you gain 1 life.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public NyxFleeceRam(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		// At the beginning of your upkeep, you gain 1 life.
		this.addAbility(new NyxFleeceRamAbility0(state));
	}
}
