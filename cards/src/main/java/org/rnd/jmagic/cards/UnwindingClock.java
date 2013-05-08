package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unwinding Clock")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({})
public final class UnwindingClock extends Card
{
	public static final class UnwindingClockAbility0 extends StaticAbility
	{
		public UnwindingClockAbility0(GameState state)
		{
			super(state, "Untap all artifacts you control during each other player's untap step.");

			SetGenerator yourArtifacts = Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance()));
			EventFactory untap = untap(yourArtifacts, "Untap all artifacts you control.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_UNTAP_EVENT);
			part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(untap));
			this.addEffectPart(part);

			this.canApply = Both.instance(this.canApply, RelativeComplement.instance(OwnerOf.instance(CurrentStep.instance()), You.instance()));

		}
	}

	public UnwindingClock(GameState state)
	{
		super(state);

		// Untap all artifacts you control during each other player's untap
		// step.
		this.addAbility(new UnwindingClockAbility0(state));
	}
}
