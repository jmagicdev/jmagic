package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Khalni Hydra")
@Types({Type.CREATURE})
@SubTypes({SubType.HYDRA})
@ManaCost("GGGGGGGG")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class KhalniHydra extends Card
{
	public static final class BargainBin extends StaticAbility
	{
		public BargainBin(GameState state)
		{
			super(state, "Khalni Hydra costs (G) less to cast for each green creature you control.");

			ContinuousEffect.Part reduction = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			reduction.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			reduction.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new ManaPool("G")));
			reduction.parameters.put(ContinuousEffectType.Parameter.NUMBER, Count.instance(Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.GREEN), ControlledBy.instance(You.instance()))));
			this.addEffectPart(reduction);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}

	public KhalniHydra(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Khalni Hydra costs (G) less to cast for each green creature you
		// control.
		this.addAbility(new BargainBin(state));

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
