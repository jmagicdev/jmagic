package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Overgrown Battlement")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class OvergrownBattlement extends Card
{
	public static final class DefenderMana extends ActivatedAbility
	{
		public DefenderMana(GameState state)
		{
			super(state, "(T): Add (G) to your mana pool for each creature with defender you control.");

			this.costsTap = true;

			SetGenerator hasDefender = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Defender.class);
			SetGenerator creaturesWithDefender = Intersect.instance(CreaturePermanents.instance(), hasDefender);
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator creaturesWithDefenderYouControl = Intersect.instance(creaturesWithDefender, youControl);

			EventFactory factory = new EventFactory(EventType.ADD_MANA, "Add (G) to your mana pool for each creature with defender you control");
			factory.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("G")));
			factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(creaturesWithDefenderYouControl));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public OvergrownBattlement(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (T): Add (G) to your mana pool for each creature with defender you
		// control.
		this.addAbility(new DefenderMana(state));
	}
}
