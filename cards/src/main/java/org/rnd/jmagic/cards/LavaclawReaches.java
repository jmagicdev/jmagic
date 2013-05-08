package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lavaclaw Reaches")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class LavaclawReaches extends Card
{
	public static final class Pump extends ActivatedAbility
	{
		public Pump(GameState state)
		{
			super(state, "(X): This creature gets +X/+0 until end of turn.");

			this.setManaCost(new ManaPool("X"));

			this.addEffect(createFloatingEffect("This creature gets +X/+0 until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, ValueOfX.instance(This.instance()), numberGenerator(0))));
		}
	}

	public static final class Animate extends ActivatedAbility
	{
		public Animate(GameState state)
		{
			super(state, "(1)(B)(R): Until end of turn, Lavaclaw Reaches becomes a 2/2 black and red Elemental creature with \"(X): This creature gets +X/+0 until end of turn.\" It's still a land.");

			this.setManaCost(new ManaPool("(1)(B)(R)"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			animator.addColor(Color.BLACK);
			animator.addColor(Color.RED);
			animator.addSubType(SubType.ELEMENTAL);
			animator.addAbility(Pump.class);
			this.addEffect(createFloatingEffect("Until end of turn, Lavaclaw Reaches becomes a 2/2 black and red Elemental creature with \"(X): This creature gets +X/+0 until end of turn.\" It's still a land.", animator.getParts()));
		}
	}

	public LavaclawReaches(GameState state)
	{
		super(state);

		// Lavaclaw Reaches enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (B) or (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BR)"));

		// (1)(B)(R): Until end of turn, Lavaclaw Reaches becomes a 2/2 black
		// and red Elemental creature with
		// "(X): This creature gets +X/+0 until end of turn." It's still a land.
		this.addAbility(new Animate(state));
	}
}
