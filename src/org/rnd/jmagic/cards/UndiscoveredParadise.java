package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Undiscovered Paradise")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.VISIONS, r = Rarity.RARE)})
@ColorIdentity({})
public final class UndiscoveredParadise extends Card
{
	public static final class UndiscoveredParadiseAbility0 extends ActivatedAbility
	{
		public UndiscoveredParadiseAbility0(GameState state)
		{
			super(state, "(T): Add one mana of any color to your mana pool. During your next untap step, as you untap your permanents, return Undiscovered Paradise to its owner's hand.");
			this.costsTap = true;
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));

			SetGenerator untilEndOfNextUntap = Intersect.instance(PreviousStep.instance(), UntapStepOf.instance(You.instance()));
			// don't bounce this on your opponents' untap steps
			SetGenerator thisCard = IfThenElse.instance(Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance()), ABILITY_SOURCE_OF_THIS, Empty.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_UNTAP_EVENT);
			part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(bounce(thisCard, "Return Undiscovered Paradise to its owner's hand.")));
			this.addEffect(createFloatingEffect(untilEndOfNextUntap, "During your next untap step, as you untap your permanents, return Undiscovered Paradise to its owner's hand.", part));
		}
	}

	public UndiscoveredParadise(GameState state)
	{
		super(state);

		// (T): Add one mana of any color to your mana pool. During your next
		// untap step, as you untap your permanents, return Undiscovered
		// Paradise to its owner's hand.
		this.addAbility(new UndiscoveredParadiseAbility0(state));
	}
}
