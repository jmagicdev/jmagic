package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Harabaz Druid")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.ALLY, SubType.HUMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class HarabazDruid extends Card
{
	public static final class AllyMana extends ActivatedAbility
	{
		public AllyMana(GameState state)
		{
			super(state, "(T): Add X mana of any one color to your mana pool, where X is the number of Allies you control.");
			this.costsTap = true;

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add X mana of any one color to your mana pool, where X is the number of Allies you control.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mana.parameters.put(EventType.Parameter.NUMBER, Count.instance(ALLIES_YOU_CONTROL));
			mana.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("(WUBRG)")));
			this.addEffect(mana);
		}
	}

	public HarabazDruid(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// (T): Add X mana of any one color to your mana pool, where X is the
		// number of Allies you control.
		this.addAbility(new AllyMana(state));
	}
}
