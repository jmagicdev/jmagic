package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sylvok Lifestaff")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class SylvokLifestaff extends Card
{
	public static final class SylvokLifestaffAbility0 extends StaticAbility
	{
		public SylvokLifestaffAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+0.");

			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +1, +0));
		}
	}

	public static final class SylvokLifestaffAbility1 extends EventTriggeredAbility
	{
		public SylvokLifestaffAbility1(GameState state)
		{
			super(state, "Whenever equipped creature dies, you gain 3 life.");
			this.addPattern(whenXDies(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public SylvokLifestaff(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+0.
		this.addAbility(new SylvokLifestaffAbility0(state));

		// Whenever equipped creature is put into a graveyard, you gain 3 life.
		this.addAbility(new SylvokLifestaffAbility1(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
