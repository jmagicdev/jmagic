package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Master of Waves")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Theros.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class MasterofWaves extends Card
{
	public static final class MasterofWavesAbility1 extends StaticAbility
	{
		public MasterofWavesAbility1(GameState state)
		{
			super(state, "Elemental creatures you control get +1/+1.");
			SetGenerator yourElementals = Intersect.instance(HasSubType.instance(SubType.ELEMENTAL), CREATURES_YOU_CONTROL);
			this.addEffectPart(modifyPowerAndToughness(yourElementals, +1, +1));
		}
	}

	public static final class MasterofWavesAbility2 extends EventTriggeredAbility
	{
		public MasterofWavesAbility2(GameState state)
		{
			super(state, "When Master of Waves enters the battlefield, put a number of 1/0 blue Elemental creature tokens onto the battlefield equal to your devotion to blue.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory elementals = new CreateTokensFactory(DevotionTo.instance(Color.BLUE), "Put a number of 1/0 blue Elemental creature tokens onto the battlefield equal to your devotion to blue.");
			elementals.addCreature(1, 0);
			elementals.setColors(Color.BLUE);
			elementals.setSubTypes(SubType.ELEMENTAL);
			this.addEffect(elementals.getEventFactory());
		}
	}

	public MasterofWaves(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Protection from red
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromRed(state));

		// Elemental creatures you control get +1/+1.
		this.addAbility(new MasterofWavesAbility1(state));

		// When Master of Waves enters the battlefield, put a number of 1/0 blue
		// Elemental creature tokens onto the battlefield equal to your devotion
		// to blue. (Each {U} in the mana costs of permanents you control counts
		// toward your devotion to blue.)
		this.addAbility(new MasterofWavesAbility2(state));
	}
}
