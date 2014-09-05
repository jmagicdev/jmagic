package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Might Weaver")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class MightWeaver extends Card
{
	public static final class Weave extends ActivatedAbility
	{
		public Weave(GameState state)
		{
			super(state, "(2): Target red or white creature gains trample until end of turn.");

			this.setManaCost(new ManaPool("2"));

			SetGenerator targets = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.RED, Color.WHITE));

			Target target = this.addTarget(targets, "target red or white creature");

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Trample.class, "Target red or white creature gains trample until end of turn"));
		}
	}

	public MightWeaver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new Weave(state));
	}
}
