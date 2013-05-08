package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rage Weaver")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RageWeaver extends Card
{
	public static final class Weave extends ActivatedAbility
	{
		public Weave(GameState state)
		{
			super(state, "(2): Target black or green creature gains haste until end of turn.");

			this.setManaCost(new ManaPool("2"));

			SetGenerator targets = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK, Color.GREEN));

			Target target = this.addTarget(targets, "target black or green creature");

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "Target black or green creature gains haste until end of turn"));
		}
	}

	public RageWeaver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new Weave(state));
	}
}
