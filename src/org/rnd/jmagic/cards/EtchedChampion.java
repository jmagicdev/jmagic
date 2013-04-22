package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Etched Champion")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SOLDIER})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class EtchedChampion extends Card
{
	public static final class EtchedChampionAbility0 extends StaticAbility
	{
		public static final class ProColors extends org.rnd.jmagic.abilities.keywords.Protection
		{
			public ProColors(GameState state)
			{
				super(state, HasColor.instance(Color.allColors()), "all colors");
			}
		}

		public EtchedChampionAbility0(GameState state)
		{
			super(state, "Etched Champion has protection from all colors as long as you control three or more artifacts.");

			this.addEffectPart(addAbilityToObject(This.instance(), ProColors.class));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public EtchedChampion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Metalcraft \u2014 Etched Champion has protection from all colors as
		// long as you control three or more artifacts.
		this.addAbility(new EtchedChampionAbility0(state));
	}
}
