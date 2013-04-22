package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sky Weaver")
@Types({Type.CREATURE})
@SubTypes({SubType.METATHRAN, SubType.WIZARD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SkyWeaver extends Card
{
	public static final class Weave extends ActivatedAbility
	{
		public Weave(GameState state)
		{
			super(state, "(2): Target white or black creature gains flying until end of turn.");

			this.setManaCost(new ManaPool("2"));

			SetGenerator whiteOrBlack = HasColor.instance(Color.WHITE, Color.BLACK);

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), whiteOrBlack), "target white or black creature");

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Flying.class, "Target white or black creature gains flying until end of turn"));
		}
	}

	public SkyWeaver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new Weave(state));
	}
}
