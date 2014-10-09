package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Leonin Iconoclast")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.MONK})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class LeoninIconoclast extends Card
{
	public static final class LeoninIconoclastAbility0 extends EventTriggeredAbility
	{
		public LeoninIconoclastAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Leonin Iconoclast, destroy target enchantment creature an opponent controls.");
			this.addPattern(heroic());

			SetGenerator enemyStuff = ControlledBy.instance(OpponentsOf.instance(You.instance()));
			SetGenerator enemyEnchantmentCreatures = Intersect.instance(enemyStuff, HasType.instance(Type.ENCHANTMENT), HasType.instance(Type.CREATURE));
			SetGenerator target = targetedBy(this.addTarget(enemyEnchantmentCreatures, "target enchantment creature an opponent controls"));
			this.addEffect(destroy(target, "Destroy target enchantment creature an opponent controls."));
		}
	}

	public LeoninIconoclast(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Heroic \u2014 Whenever you cast a spell that targets Leonin
		// Iconoclast, destroy target enchantment creature an opponent controls.
		this.addAbility(new LeoninIconoclastAbility0(state));
	}
}
