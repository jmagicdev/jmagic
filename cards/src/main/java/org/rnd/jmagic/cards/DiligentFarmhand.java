package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Diligent Farmhand")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.DRUID})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Odyssey.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class DiligentFarmhand extends Card
{
	public static final class DiligentFarmhandAbility0 extends ActivatedAbility
	{
		public DiligentFarmhandAbility0(GameState state)
		{
			super(state, "(1)(G), Sacrifice Diligent Farmhand: Search your library for a basic land card and put that card onto the battlefield tapped. Then shuffle your library.");

			this.setManaCost(new ManaPool("(1)(G)"));

			this.addCost(sacrificeThis("Diligent Farmhand"));

			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public static final class CountsAsMuscleBurst extends StaticAbility
	{
		public CountsAsMuscleBurst(GameState state)
		{
			super(state, "If Diligent Farmhand is in a graveyard, effects from spells named Muscle Burst count it as a card named Muscle Burst.");

			this.canApply = THIS_IS_IN_A_GRAVEYARD;

			SetGenerator muscleBurst = Identity.instance("Muscle Burst");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MODIFY_HAS_NAME);
			part.parameters.put(ContinuousEffectType.Parameter.EFFECT, muscleBurst);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.NAME, muscleBurst);
			this.addEffectPart(part);
		}
	}

	public DiligentFarmhand(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(G), Sacrifice Diligent Farmhand: Search your library for a basic
		// land card and put that card onto the battlefield tapped. Then shuffle
		// your library.
		this.addAbility(new DiligentFarmhandAbility0(state));

		// If Diligent Farmhand is in a graveyard, effects from spells named
		// Muscle Burst count it as a card named Muscle Burst.
		this.addAbility(new CountsAsMuscleBurst(state));
	}
}
