package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cave-In")
@Types({Type.SORCERY})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = MercadianMasques.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class CaveIn extends Card
{
	public CaveIn(GameState state)
	{
		super(state);

		// You may exile a red card from your hand rather than pay Cave-In's
		// mana cost.
		EventFactory exileFactory = new EventFactory(EventType.EXILE_CHOICE, "Exile a red card from your hand");
		exileFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exileFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		exileFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.RED), InZone.instance(HandOf.instance(You.instance()))));
		exileFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, exileFactory);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may exile a red card from your hand rather than pay Cave-In's mana cost.", altCost));

		// Cave-In deals 2 damage to each creature and each player.
		this.addEffect(spellDealDamage(2, CREATURES_AND_PLAYERS, "Cave-In deals 2 damage to each creature and each player."));
	}
}
