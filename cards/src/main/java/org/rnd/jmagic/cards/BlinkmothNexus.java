package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Blinkmoth Nexus")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Darksteel.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class BlinkmothNexus extends Card
{
	public static final class BlinkmothNexusAbility1 extends ActivatedAbility
	{
		public BlinkmothNexusAbility1(GameState state)
		{
			super(state, "(1): Blinkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying until end of turn. It's still a land.");
			this.setManaCost(new ManaPool("(1)"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 1, 1);
			animator.addSubType(SubType.BLINKMOTH);
			animator.addType(Type.ARTIFACT);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(createFloatingEffect("Blinkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying until end of turn. It's still a land.", animator.getParts()));
		}
	}

	public static final class BlinkmothNexusAbility2 extends ActivatedAbility
	{
		public BlinkmothNexusAbility2(GameState state)
		{
			super(state, "(1), (T): Target Blinkmoth creature gets +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(HasSubType.instance(SubType.BLINKMOTH), CreaturePermanents.instance()), "target Blinkmoth creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), +1, +1, "Target Blinkmoth creature gets +1/+1 until end of turn."));
		}
	}

	public BlinkmothNexus(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1): Blinkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with
		// flying until end of turn. It's still a land.
		this.addAbility(new BlinkmothNexusAbility1(state));

		// (1), (T): Target Blinkmoth creature gets +1/+1 until end of turn.
		this.addAbility(new BlinkmothNexusAbility2(state));
	}
}
