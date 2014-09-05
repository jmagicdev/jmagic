package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Llanowar")
@Types({Type.PLANE})
@SubTypes({SubType.DOMINARIA})
@Printings({@Printings.Printed(ex = org.rnd.jmagic.expansions.Planechase.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Llanowar extends Card
{
	public static final class ElvesDoItWithMana extends StaticAbility
	{
		public static final class TapForGG extends org.rnd.jmagic.abilities.TapForMana
		{
			public TapForGG(GameState state)
			{
				super(state, "(G)(G)");
			}
		}

		public ElvesDoItWithMana(GameState state)
		{
			super(state, "All creatures have \"(T): Add (G)(G) to your mana pool.\"");

			this.addEffectPart(addAbilityToObject(CreaturePermanents.instance(), TapForGG.class));

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class VigilantChaos extends EventTriggeredAbility
	{
		public VigilantChaos(GameState state)
		{
			super(state, "Whenever you roll (C), untap all creatures you control.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			this.addEffect(untap(CREATURES_YOU_CONTROL, "Untap all creatures you control."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Llanowar(GameState state)
	{
		super(state);

		this.addAbility(new ElvesDoItWithMana(state));

		this.addAbility(new VigilantChaos(state));
	}
}
