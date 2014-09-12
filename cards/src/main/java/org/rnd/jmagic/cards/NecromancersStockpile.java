package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Necromancer's Stockpile")
@Types({Type.ENCHANTMENT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class NecromancersStockpile extends Card
{
	public static final class NecromancersStockpileAbility0 extends ActivatedAbility
	{
		public NecromancersStockpileAbility0(GameState state)
		{
			super(state, "(1)(B), Discard a creature card: Draw a card. If the discarded card was a Zombie card, put a 2/2 black Zombie creature token onto the battlefield tapped.");
			this.setManaCost(new ManaPool("(1)(B)"));

			EventFactory discard = discardCards(You.instance(), 1, HasType.instance(Type.CREATURE), "Discard a creature card");
			this.addCost(discard);

			this.addEffect(drawACard());

			SetGenerator discarded = OldObjectOf.instance(CostResult.instance(discard));
			SetGenerator discardedZombie = Intersect.instance(discarded, HasSubType.instance(SubType.ZOMBIE));

			CreateTokensFactory zombie = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield tapped.");
			zombie.setColors(Color.BLACK);
			zombie.setSubTypes(SubType.ZOMBIE);
			zombie.setTapped();
			this.addEffect(ifThen(discardedZombie, zombie.getEventFactory(), "If the discarded card was a Zombie card, put a 2/2 black Zombie creature token onto the battlefield tapped."));

		}
	}

	public NecromancersStockpile(GameState state)
	{
		super(state);

		// (1)(B), Discard a creature card: Draw a card. If the discarded card
		// was a Zombie card, put a 2/2 black Zombie creature token onto the
		// battlefield tapped.
		this.addAbility(new NecromancersStockpileAbility0(state));
	}
}
