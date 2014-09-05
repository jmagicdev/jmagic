package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mishra's Factory")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Antiquities.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class MishrasFactory extends Card
{
	public static final class AnimateFactory extends ActivatedAbility
	{
		public AnimateFactory(GameState state)
		{
			super(state, "(1): Mishra's Factory becomes a 2/2 Assembly-Worker artifact creature until end of turn. It's still a land.");

			this.setManaCost(new ManaPool("1"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			animator.addSubType(SubType.ASSEMBLY_WORKER);
			animator.addType(Type.ARTIFACT);
			this.addEffect(createFloatingEffect("Mishra's Factory becomes a 2/2 Assembly-Worker artifact creature until end of turn. It's still a land.", animator.getParts()));
		}
	}

	public static final class PumpFactory extends ActivatedAbility
	{
		public PumpFactory(GameState state)
		{
			super(state, "(T): Target Assembly-Worker creature gets +1/+1 until end of turn.");
			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(HasSubType.instance(SubType.ASSEMBLY_WORKER), CreaturePermanents.instance()), "target Assembly-Worker creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), +1, +1, "Target Assembly-Worker creature gets +1/+1 until end of turn."));
		}
	}

	public MishrasFactory(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1): Mishra's Factory becomes a 2/2 Assembly-Worker artifact creature
		// until end of turn. It's still a land.
		this.addAbility(new AnimateFactory(state));

		// (T): Target Assembly-Worker creature gets +1/+1 until end of turn.
		this.addAbility(new PumpFactory(state));
	}
}
