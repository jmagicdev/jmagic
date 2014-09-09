package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Petrified Field")
@Types({Type.LAND})
@ColorIdentity({})
public final class PetrifiedField extends Card
{
	public static final class PetrifiedFieldAbility1 extends ActivatedAbility
	{
		public PetrifiedFieldAbility1(GameState state)
		{
			super(state, "(T), Sacrifice Petrified Field: Return target land card from your graveyard to your hand.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Petrified Field"));

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator deadLands = Intersect.instance(HasType.instance(Type.LAND), InZone.instance(yourGraveyard));
			SetGenerator target = targetedBy(this.addTarget(deadLands, "target land card from your graveyard"));
			this.addEffect(putIntoHand(target, You.instance(), "Return target land card from your graveyard to your hand."));
		}
	}

	public PetrifiedField(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T), Sacrifice Petrified Field: Return target land card from your
		// graveyard to your hand.
		this.addAbility(new PetrifiedFieldAbility1(state));
	}
}
