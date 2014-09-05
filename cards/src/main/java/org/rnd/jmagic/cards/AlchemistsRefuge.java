package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Alchemist's Refuge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class AlchemistsRefuge extends Card
{
	public static final class AlchemistsRefugeAbility1 extends ActivatedAbility
	{
		public AlchemistsRefugeAbility1(GameState state)
		{
			super(state, "(G)(U), (T): You may cast nonland cards this turn as though they had flash.");
			this.setManaCost(new ManaPool("(G)(U)"));
			this.costsTap = true;

			SetGenerator youHavePriority = Intersect.instance(You.instance(), PlayerWithPriority.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_TIMING);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(Cards.instance(), HasType.instance(Type.LAND)));
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(youHavePriority)));

			this.addEffect(createFloatingEffect("You may cast nonland cards this turn as though they had flash.", part));
		}
	}

	public AlchemistsRefuge(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (G)(U), (T): You may cast nonland cards this turn as though they had
		// flash.
		this.addAbility(new AlchemistsRefugeAbility1(state));
	}
}
