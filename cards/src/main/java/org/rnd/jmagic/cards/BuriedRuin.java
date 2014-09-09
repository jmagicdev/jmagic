package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Buried Ruin")
@Types({Type.LAND})
@ColorIdentity({})
public final class BuriedRuin extends Card
{
	public static final class BuriedRuinAbility1 extends ActivatedAbility
	{
		public BuriedRuinAbility1(GameState state)
		{
			super(state, "(2), (T), Sacrifice Buried Ruin: Return target artifact card from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Buried Ruin"));

			SetGenerator yourYard = GraveyardOf.instance(You.instance());
			SetGenerator legal = Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(yourYard));
			SetGenerator target = targetedBy(this.addTarget(legal, "target artifact card from your graveyard"));
			this.addEffect(putIntoHand(target, You.instance(), "Return target artifact card from your graveyard to your hand."));
		}
	}

	public BuriedRuin(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (2), (T), Sacrifice Buried Ruin: Return target artifact card from
		// your graveyard to your hand.
		this.addAbility(new BuriedRuinAbility1(state));
	}
}
