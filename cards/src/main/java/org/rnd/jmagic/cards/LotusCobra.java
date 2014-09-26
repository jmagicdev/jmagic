package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lotus Cobra")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class LotusCobra extends Card
{
	public static final class LandfallMana extends EventTriggeredAbility
	{
		public LandfallMana(GameState state)
		{
			super(state, "Landfall \u2014 Whenever a land enters the battlefield under your control, you may add one mana of any color to your mana pool.");
			this.addPattern(landfall());

			EventFactory makeMana = new EventFactory(EventType.ADD_MANA, "Add one mana of any color to your mana pool");
			makeMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			makeMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			makeMana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(WUBRG)")));

			this.addEffect(youMay(makeMana, "You may add one mana of any color to your mana pool."));
		}
	}

	public LotusCobra(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may add one mana of any color to your mana pool.
		this.addAbility(new LandfallMana(state));
	}
}
