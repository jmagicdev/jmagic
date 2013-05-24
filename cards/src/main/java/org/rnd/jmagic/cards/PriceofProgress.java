package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Price of Progress")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.EXODUS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class PriceofProgress extends Card
{
	public PriceofProgress(GameState state)
	{
		super(state);

		// Price of Progress deals damage to each player equal to twice the
		// number of nonbasic lands that player controls.
		DynamicEvaluation eachPlayer = DynamicEvaluation.instance();

		SetGenerator nonbasics = RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC));
		SetGenerator thatPlayersNonbasics = Intersect.instance(ControlledBy.instance(eachPlayer), nonbasics);
		SetGenerator amount = Multiply.instance(numberGenerator(2), Count.instance(thatPlayersNonbasics));
		EventFactory dealDamage = spellDealDamage(amount, eachPlayer, "Price of Progress deals damage to that player equal to twice the number of nonbasic lands he or she controls.");

		EventFactory effect = new EventFactory(FOR_EACH_PLAYER, "Price of Progress deals damage to each player equal to twice the number of nonbasic lands that player controls.");
		effect.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		effect.parameters.put(EventType.Parameter.EFFECT, Identity.instance(dealDamage));
		this.addEffect(effect);
	}
}
