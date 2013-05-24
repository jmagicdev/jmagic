package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Seedborn Muse")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class SeedbornMuse extends Card
{
	public static final class UntapYourPermanents extends StaticAbility
	{
		public UntapYourPermanents(GameState state)
		{
			super(state, "Untap all permanents you control during each other player's untap step.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_UNTAP_EVENT);
			part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(untap(ControlledBy.instance(You.instance()), "Untap all permanents you control.")));
			this.addEffectPart(part);

			this.canApply = Both.instance(this.canApply, RelativeComplement.instance(OwnerOf.instance(CurrentStep.instance()), You.instance()));
		}
	}

	public SeedbornMuse(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		this.addAbility(new UntapYourPermanents(state));
	}
}
